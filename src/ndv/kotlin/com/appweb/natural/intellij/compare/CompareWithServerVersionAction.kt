package com.appweb.natural.intellij.compare

import com.appweb.natural.intellij.nds.NdsClient
import com.appweb.natural.intellij.nds.NdsObjectInfo
import com.appweb.natural.intellij.nds.NdsServer
import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

/**
 * Editor / project-view action: given a local Natural file, find the matching object on a
 * configured Natural Development Server and open the standard diff viewer (local vs server).
 *
 * Resolution:
 *  - Object name  = file name without extension, uppercased
 *  - Extension    = file extension, uppercased
 *  - Library name = parent directory name, uppercased (used as initial guess)
 *  - Server       = the single configured server, or a chooser if there are several
 */
class CompareWithServerVersionAction : AnAction() {

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        val visible = e.project != null
            && file != null
            && file !is LightVirtualFile
            && isNaturalFile(file)
        e.presentation.isEnabledAndVisible = visible
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        if (file is LightVirtualFile || !isNaturalFile(file)) return

        val servers = NdsServerSettings.getInstance().servers
        if (servers.isEmpty()) {
            Messages.showInfoMessage(project,
                "No Natural Development Servers are configured. Open the Natural Servers tool window to add one.",
                "Compare with Server")
            return
        }

        val server = if (servers.size == 1) servers.first() else chooseServer(project, servers) ?: return

        val objectName = file.nameWithoutExtension.uppercase()
        val extension = (file.extension ?: return).uppercase()
        val libraryGuess = file.parent?.name?.uppercase()

        resolveAndCompare(project, server, libraryGuess, objectName, extension, file)
    }

    private fun chooseServer(project: Project, servers: List<NdsServer>): NdsServer? {
        val labels = servers.map { it.displayName }.toTypedArray()
        val idx = Messages.showChooseDialog(
            project,
            "Choose the server to compare against:",
            "Compare with Server",
            null,
            labels,
            labels.first(),
        )
        return if (idx in servers.indices) servers[idx] else null
    }

    private fun resolveAndCompare(
        project: Project,
        server: NdsServer,
        libraryGuess: String?,
        objectName: String,
        extension: String,
        localFile: VirtualFile,
    ) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(
            project, "Looking up $objectName on ${server.displayName}", true
        ) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                try {
                    val password = NdsServerSettings.getInstance().getPassword(server)
                    NdsClient.connect(
                        server.host, server.port, server.user, password, server.logonLibrary
                    ).use { client ->
                        val library = pickLibrary(project, client, libraryGuess) ?: return@use
                        val info = findObject(client, library, objectName, extension)
                        if (info == null) {
                            onEdt {
                                Messages.showInfoMessage(
                                    project,
                                    "$objectName.$extension was not found in library '$library' on ${server.displayName}.",
                                    "Compare with Server",
                                )
                            }
                            return@use
                        }
                        onEdt {
                            NaturalCompareService.showDiff(project, server, library, info, localFile)
                        }
                    }
                } catch (e: Throwable) {
                    onEdt {
                        Messages.showErrorDialog(
                            project,
                            "Could not contact ${server.displayName}:\n${e.message ?: e.javaClass.simpleName}",
                            "Compare with Server",
                        )
                    }
                }
            }
        })
    }

    /**
     * Returns the library to query: the guess if it exists on the server, otherwise prompts the
     * user with the list of libraries.
     */
    private fun pickLibrary(project: Project, client: NdsClient, guess: String?): String? {
        val libraries = client.listLibraries()
        if (guess != null && libraries.any { it.equals(guess, ignoreCase = true) }) {
            return libraries.first { it.equals(guess, ignoreCase = true) }
        }
        if (libraries.isEmpty()) {
            onEdt {
                Messages.showInfoMessage(project, "Server has no libraries.", "Compare with Server")
            }
            return null
        }
        var chosen: String? = null
        ApplicationManager.getApplication().invokeAndWait {
            val labels = libraries.toTypedArray()
            val idx = Messages.showChooseDialog(
                project,
                if (guess != null) "Library '$guess' was not found on the server. Choose a library:"
                else "Choose the library that contains this object:",
                "Compare with Server",
                null,
                labels,
                labels.first(),
            )
            if (idx in libraries.indices) chosen = libraries[idx]
        }
        return chosen
    }

    private fun findObject(
        client: NdsClient,
        library: String,
        objectName: String,
        extension: String,
    ): NdsObjectInfo? = client.listObjects(library)
        .firstOrNull {
            it.name.equals(objectName, ignoreCase = true)
                && it.extension.equals(extension, ignoreCase = true)
        }

    private fun onEdt(body: () -> Unit) {
        ApplicationManager.getApplication().invokeLater(body)
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
