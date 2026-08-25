package com.appweb.natural.intellij.debug

import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.NopProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.filters.TextConsoleBuilderFactory

/**
 * Plain RunProfileState that builds an empty console + no-op process handler. The actual debug
 * session is opened lazily by [NaturalDebugRunner], because we need an [XDebugSession] to attach
 * the [NdsDebugSession] to.
 */
class NaturalDebugRunProfileState(
    private val environment: ExecutionEnvironment,
    val config: NaturalDebugRunConfiguration,
) : RunProfileState {

    override fun execute(executor: Executor?, runner: ProgramRunner<*>): ExecutionResult {
        val console = TextConsoleBuilderFactory.getInstance().createBuilder(environment.project).console
        val handler = NopProcessHandler()
        console.attachToProcess(handler)
        return DefaultExecutionResult(console, handler)
    }
}
