package com.appweb.natural.intellij.debug

import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.GenericProgramRunner
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugProcessStarter
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerManager

class NaturalDebugRunner : GenericProgramRunner<com.intellij.execution.configurations.RunnerSettings>() {

    override fun getRunnerId(): String = "Natural.Debug.Runner"

    override fun canRun(executorId: String, profile: RunProfile): Boolean =
        executorId == DefaultDebugExecutor.EXECUTOR_ID && profile is NaturalDebugRunConfiguration

    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment): RunContentDescriptor? {
        val config = (state as? NaturalDebugRunProfileState)?.config
            ?: throw ExecutionException("Unexpected run profile state")
        val server = NdsServerSettings.getInstance().findById(config.serverId)
            ?: throw ExecutionException("Configured NDV server not found")
        val password = NdsServerSettings.getInstance().getPassword(server)
        val library = config.library.uppercase()
        val obj = config.obj.uppercase()
        val parameter = config.parameter

        val xSession = XDebuggerManager.getInstance(environment.project).startSession(
            environment,
            object : XDebugProcessStarter() {
                override fun start(session: XDebugSession): XDebugProcess {
                    val executionResult = state.execute(environment.executor, this@NaturalDebugRunner)
                    val console = executionResult.executionConsole as com.intellij.execution.ui.ConsoleView
                    val debugSession = NdsDebugSession.connect(
                        host = server.host,
                        port = server.port,
                        user = server.user,
                        password = password,
                        library = library,
                        obj = obj,
                        parameter = parameter,
                    )
                    val locator = NaturalSourceLocator(environment.project)
                    return NaturalDebugProcess(session, debugSession, locator, console)
                }
            },
        )
        return xSession.runContentDescriptor
    }
}
