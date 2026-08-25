package com.appweb.natural.intellij.actions

import com.appweb.natural.intellij.nds.NdsClient
import com.appweb.natural.intellij.nds.NdsCompileError
import com.appweb.natural.intellij.nds.NdsServer
import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.codeInsight.highlighting.HighlightManager
import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import com.intellij.ui.JBColor
import javax.swing.Icon

/**
 * "Stow" the current Natural file on the configured Natural Development Server. Sends the
 * editor text, compiles, and stores both source and generated program on the server.
 */
class StowAction : NdsCompileActionBase(verb = "Stow", successPast = "stowed to") {
    override fun invoke(client: NdsClient, library: String, name: String, ext: String, src: List<String>) =
        client.stow(library, name, ext, src)
}

/**
 * "Check" the current Natural file: syntax-check on the server without saving or cataloguing.
 */
class CheckAction : NdsCompileActionBase(verb = "Check", successPast = "passed check in") {
    override fun invoke(client: NdsClient, library: String, name: String, ext: String, src: List<String>) =
        client.check(library, name, ext, src)
}

abstract class NdsCompileActionBase(
    private val verb: String,
    private val successPast: String,
) : AnAction() {

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = e.project != null
            && file != null
            && file !is LightVirtualFile
            && isNaturalFile(file)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        if (file is LightVirtualFile || !isNaturalFile(file)) return

        val servers = NdsServerSettings.getInstance().servers
        if (servers.isEmpty()) {
            Messages.showInfoMessage(
                project,
                "No Natural Development Servers are configured. Open the Natural Servers tool window to add one.",
                "$verb on Server",
            )
            return
        }
        val server = if (servers.size == 1) servers.first() else chooseServer(project, servers) ?: return

        val objectName = file.nameWithoutExtension.uppercase()
        val extension = file.extension?.uppercase() ?: return
        val libraryGuess = file.parent?.name?.uppercase()

        val sourceLines = readSourceLines(file) ?: run {
            Messages.showErrorDialog(project, "Could not read $objectName.$extension", "$verb on Server")
            return
        }

        runOnServer(project, server, libraryGuess, objectName, extension, file, sourceLines)
    }

    protected abstract fun invoke(
        client: NdsClient,
        library: String,
        name: String,
        ext: String,
        src: List<String>,
    )

    private fun runOnServer(
        project: Project,
        server: NdsServer,
        libraryGuess: String?,
        objectName: String,
        extension: String,
        file: VirtualFile,
        sourceLines: List<String>,
    ) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(
            project, "${verb}ing $objectName on ${server.displayName}", true
        ) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                try {
                    val password = NdsServerSettings.getInstance().getPassword(server)
                    NdsClient.connect(
                        server.host, server.port, server.user, password, server.logonLibrary
                    ).use { client ->
                        val library = pickLibrary(project, client, libraryGuess) ?: return@use
                        invoke(client, library, objectName, extension, sourceLines)
                        notify(project, NotificationType.INFORMATION,
                            "$verb succeeded",
                            "$objectName.$extension $successPast $library on ${server.displayName}.")
                    }
                } catch (e: NdsCompileError) {
                    onEdt { handleCompileError(project, file, e) }
                } catch (e: Throwable) {
                    onEdt {
                        Messages.showErrorDialog(
                            project,
                            "${verb} of $objectName failed on ${server.displayName}:\n${e.message ?: e.javaClass.simpleName}",
                            "$verb on Server",
                        )
                    }
                }
            }
        })
    }

    private fun handleCompileError(project: Project, file: VirtualFile, err: NdsCompileError) {
        val detail = buildString {
            append("NAT").append(err.errorNumber).append(" at line ").append(err.row)
            if (err.column > 0) append(", column ").append(err.column)
            append(": ").append(err.shortText)
            if (err.longText.isNotEmpty()) {
                append("\n\n")
                append(err.longText.joinToString("\n"))
            }
        }
        notify(project, NotificationType.ERROR, "$verb failed: ${err.objectName}", detail)
        jumpToError(project, file, err)
    }

    private fun jumpToError(project: Project, file: VirtualFile, err: NdsCompileError) {
        if (err.row <= 0) return
        val line = err.row - 1
        val col = (err.column - 1).coerceAtLeast(0)
        val descriptor = OpenFileDescriptor(project, file, line, col)
        val editor = FileEditorManager.getInstance(project).openTextEditor(descriptor, true) ?: return

        val document = editor.document
        if (line >= document.lineCount) return
        val lineStart = document.getLineStartOffset(line)
        val lineEnd = document.getLineEndOffset(line)

        val highlighters = mutableListOf<RangeHighlighter>()
        HighlightManager.getInstance(project).addRangeHighlight(
            editor,
            lineStart,
            lineEnd,
            CodeInsightColors.ERRORS_ATTRIBUTES,
            /* hideByTextChange = */ true,
            highlighters,
        )

        val tooltip = buildErrorTooltip(err)
        val iconRenderer = CompileErrorGutterRenderer(tooltip)
        for (h in highlighters) {
            h.setErrorStripeMarkColor(JBColor.RED)
            h.setErrorStripeTooltip(tooltip)
            h.gutterIconRenderer = iconRenderer
        }
    }

    private fun buildErrorTooltip(err: NdsCompileError): String = buildString {
        append("<html><b>NAT").append(err.errorNumber).append("</b> at line ").append(err.row)
        if (err.column > 0) append(", column ").append(err.column)
        append("<br>").append(StringUtil.escapeXmlEntities(err.shortText))
        if (err.longText.isNotEmpty()) {
            append("<br><br>")
            err.longText.joinTo(this, separator = "<br>") { StringUtil.escapeXmlEntities(it) }
        }
        append("</html>")
    }

    private class CompileErrorGutterRenderer(private val tooltip: String) : GutterIconRenderer() {
        override fun getIcon(): Icon = AllIcons.General.Error
        override fun getTooltipText(): String = tooltip
        override fun equals(other: Any?): Boolean =
            other is CompileErrorGutterRenderer && other.tooltip == tooltip
        override fun hashCode(): Int = tooltip.hashCode()
    }

    private fun pickLibrary(project: Project, client: NdsClient, guess: String?): String? {
        val libraries = client.listLibraries()
        if (guess != null && libraries.any { it.equals(guess, ignoreCase = true) }) {
            return libraries.first { it.equals(guess, ignoreCase = true) }
        }
        if (libraries.isEmpty()) {
            onEdt { Messages.showInfoMessage(project, "Server has no libraries.", "$verb on Server") }
            return null
        }
        var chosen: String? = null
        ApplicationManager.getApplication().invokeAndWait {
            val labels = libraries.toTypedArray()
            val idx = Messages.showChooseDialog(
                project,
                if (guess != null) "Library '$guess' was not found on the server. Choose a library:"
                else "Choose the target library:",
                "$verb on Server",
                null,
                labels,
                labels.first(),
            )
            if (idx in libraries.indices) chosen = libraries[idx]
        }
        return chosen
    }

    private fun chooseServer(project: Project, servers: List<NdsServer>): NdsServer? {
        val labels = servers.map { it.displayName }.toTypedArray()
        val idx = Messages.showChooseDialog(
            project, "Choose the server:", "$verb on Server", null, labels, labels.first(),
        )
        return if (idx in servers.indices) servers[idx] else null
    }

    private fun readSourceLines(file: VirtualFile): List<String>? {
        val doc = FileDocumentManager.getInstance().getDocument(file) ?: return null
        // Persist any pending edits before we send the source.
        ApplicationManager.getApplication().invokeAndWait {
            FileDocumentManager.getInstance().saveDocument(doc)
        }
        return doc.text.split('\n').map { it.removeSuffix("\r") }
    }

    private fun onEdt(body: () -> Unit) {
        ApplicationManager.getApplication().invokeLater(body)
    }

    private fun notify(project: Project, type: NotificationType, title: String, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Natural.Server")
            .createNotification(title, content, type)
            .notify(project)
    }

    companion object {
        private val NATURAL_EXTENSIONS = setOf(
            "NSP", "NSN", "NSS", "NSC", "NSH", "NSM",
            "NSL", "NSA", "NSG", "NS4", "NS7", "NST", "NSE",
        )

        private fun isNaturalFile(file: VirtualFile): Boolean =
            file.extension?.uppercase() in NATURAL_EXTENSIONS
    }
}
