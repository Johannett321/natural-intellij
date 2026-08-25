package com.appweb.natural.intellij.run

import com.intellij.openapi.project.Project
import com.intellij.terminal.pty.PtyProcessTtyConnector
import com.jediterm.terminal.TtyConnector
import com.pty4j.PtyProcess
import org.jetbrains.plugins.terminal.LocalTerminalDirectRunner
import java.nio.charset.Charset

// Subclassing solely to lock in ISO-8859-1 for the TTY connector;
// the shell command is passed via ShellStartupOptions, not getInitialCommand.

class NaturalTerminalRunner(project: Project) : LocalTerminalDirectRunner(project) {
    override fun createTtyConnector(process: PtyProcess): TtyConnector =
        PtyProcessTtyConnector(process, Charset.forName("ISO-8859-1"))
}
