package com.appweb.natural.intellij.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.ToolWindowType
import com.intellij.terminal.JBTerminalWidget
import com.intellij.ui.content.ContentFactory
import com.jediterm.core.util.TermSize
import com.pty4j.PtyProcess
import org.jetbrains.plugins.terminal.shellStartupOptions

class NaturalRunProfileState(
    private val environment: ExecutionEnvironment,
    private val config: NaturalRunConfiguration
) : RunProfileState {

    companion object {
        const val TOOL_WINDOW_ID = "Natural Run"
    }

    override fun execute(executor: Executor?, runner: ProgramRunner<*>): ExecutionResult? {
        val project = environment.project

        val file = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            ?: throw ExecutionException("No file is currently open in the editor")

        val ext = file.extension?.uppercase()
        if (ext !in setOf("NSP", "NSN", "NSS", "NSC")) {
            throw ExecutionException("Active file is not a runnable Natural program (found .$ext)")
        }

        val projectBase = project.basePath ?: throw ExecutionException("Cannot determine project base path")
        val relative = file.path.removePrefix(projectBase).trimStart('/')
        val parts = relative.split("/")
        if (parts.size < 2) throw ExecutionException("Program must be inside a library directory under the project root")

        val library = parts[0]
        val programName = file.nameWithoutExtension
        val sshCommand = buildSshCommand(library, programName)
        val tabName = "$programName @ ${config.host}"

        ApplicationManager.getApplication().invokeLater({
            val toolWindowManager = ToolWindowManager.getInstance(project)
            val toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID) ?: return@invokeLater

            val terminalRunner = NaturalTerminalRunner(project)
            val settingsProvider = NaturalTerminalSettingsProvider()
            val disposable = Disposer.newDisposable("NaturalRun:$tabName")
            val widget = JBTerminalWidget(project, 80, 24, settingsProvider, null, disposable)

            var process: PtyProcess? = null
            try {
                val options = shellStartupOptions(projectBase) { builder ->
                    builder.shellCommand(listOf("bash", "-c", sshCommand))
                    builder.initialTermSize(TermSize(80, 24))
                }
                process = terminalRunner.createProcess(options)
                val connector = terminalRunner.createTtyConnector(process)
                widget.start(connector)
            } catch (e: Exception) {
                Disposer.dispose(disposable)
                Messages.showErrorDialog(project, e.message ?: "Failed to start SSH session", "Natural Run Error")
                return@invokeLater
            }

            val content = ContentFactory.getInstance().createContent(widget, tabName, false)
            content.isCloseable = true
            Disposer.register(content, disposable)

            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)
            if (!toolWindow.isAvailable) {
                toolWindow.setType(ToolWindowType.WINDOWED, null)
            }
            toolWindow.isAvailable = true
            toolWindow.activate(null)

            process?.onExit()?.thenRun {
                ApplicationManager.getApplication().invokeLater({
                    toolWindow.contentManager.removeContent(content, true)
                }, ModalityState.nonModal())
            }
        }, ModalityState.nonModal())

        return null
    }

    // Produces: ssh -t user@host [-p port] "bash -l -c 'natural [parm=...] STACK=\"(LOGON lib;prog;FIN)\"'"
    private fun buildSshCommand(library: String, programName: String): String {
        val naturalCmd = buildString {
            append("natural")
            if (config.parms.isNotBlank()) append(" parm=${config.parms}")
            append(" STACK=\\\"(LOGON $library;$programName;FIN)\\\"")
        }

        return buildString {
            val password = config.password
            if (password.isNotEmpty() && isSshpassAvailable()) {
                val escapedPass = password.replace("'", "'\\''")
                append("sshpass -p '$escapedPass' ")
            }
            append("ssh -t ${config.username}@${config.host}")
            if (config.port != 22) append(" -p ${config.port}")
            append(" \"bash -l -c '$naturalCmd'\"")
        }
    }

    private fun isSshpassAvailable(): Boolean = try {
        ProcessBuilder("which", "sshpass").start().waitFor() == 0
    } catch (_: Exception) {
        false
    }
}
