package com.appweb.natural.intellij.debug

import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XBreakpointProperties
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import com.intellij.xdebugger.frame.XSuspendContext

class NaturalDebugProcess(
    session: XDebugSession,
    private val sessionRunner: NdsDebugSession,
    private val locator: NaturalSourceLocator,
    private val consoleView: ConsoleView,
) : XDebugProcess(session) {

    private val log = Logger.getInstance(NaturalDebugProcess::class.java)
    private val editorsProvider = NaturalDebuggerEditorsProvider()
    private val breakpointHandler = NaturalBreakpointHandler(this)
    private val registered = mutableMapOf<BpKey, XLineBreakpoint<*>>()

    init {
        sessionRunner.setListener(object : NdsDebugSession.Listener {
            override fun onSuspended(info: NdsDebugSession.SuspendInfo) = handleSuspended(info)
            override fun onTerminated(reason: String?) = handleTerminated(reason)
            override fun onConsoleOutput(text: String) {
                consoleView.print(text, ConsoleViewContentType.NORMAL_OUTPUT)
            }
            override fun onError(t: Throwable) {
                consoleView.print("Debug error: ${t.message}\n", ConsoleViewContentType.ERROR_OUTPUT)
            }
        })
    }

    override fun getEditorsProvider(): XDebuggerEditorsProvider = editorsProvider

    override fun getBreakpointHandlers(): Array<XBreakpointHandler<*>> = arrayOf(breakpointHandler)

    override fun sessionInitialized() {
        // Kick off the run on a background thread — `start()` blocks until the runtime first
        // suspends (entry breakpoint at the first executable line).
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                consoleView.print("Connecting to NDV and starting debug session...\n",
                    ConsoleViewContentType.SYSTEM_OUTPUT)
                sessionRunner.start()
            } catch (t: Throwable) {
                log.warn("Failed to start debug session", t)
                consoleView.print("Failed to start: ${t.message}\n", ConsoleViewContentType.ERROR_OUTPUT)
                session.stop()
            }
        }
    }

    override fun startStepOver(context: XSuspendContext?) = onPooled { sessionRunner.stepOver() }
    override fun startStepInto(context: XSuspendContext?) = onPooled { sessionRunner.stepInto() }
    override fun startStepOut(context: XSuspendContext?) = onPooled { sessionRunner.stepReturn() }
    override fun resume(context: XSuspendContext?) = onPooled { sessionRunner.resume() }
    override fun startPausing() {
        // The Pal protocol has no explicit "suspend"; closest analogue is a one-shot breakpoint.
        // Not supported for now.
    }

    override fun runToPosition(position: XSourcePosition, context: XSuspendContext?) {
        val coord = locator.coordinates(position.file) ?: return
        val line1 = position.line + 1
        onPooled { sessionRunner.runToLine(coord.library, coord.obj, line1) }
    }

    override fun stop() {
        try { sessionRunner.close() } catch (t: Throwable) { log.warn(t) }
    }

    // -- breakpoint plumbing --

    fun registerBreakpoint(bp: XLineBreakpoint<XBreakpointProperties<*>>) {
        val pos = bp.sourcePosition ?: return
        val coord = locator.coordinates(pos.file) ?: return
        val key = BpKey(coord.library, coord.obj, pos.line + 1)
        registered[key] = bp
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                sessionRunner.setBreakpoint(coord.library, coord.obj, pos.line + 1)
            } catch (t: Throwable) {
                session.setBreakpointInvalid(bp, t.message ?: t.javaClass.simpleName)
            }
        }
    }

    fun unregisterBreakpoint(bp: XLineBreakpoint<XBreakpointProperties<*>>) {
        val pos = bp.sourcePosition ?: return
        val coord = locator.coordinates(pos.file) ?: return
        val key = BpKey(coord.library, coord.obj, pos.line + 1)
        registered.remove(key)
        ApplicationManager.getApplication().executeOnPooledThread {
            try { sessionRunner.removeBreakpoint(coord.library, coord.obj, pos.line + 1) }
            catch (t: Throwable) { log.warn("removeBreakpoint failed", t) }
        }
    }

    // -- suspend / terminate handling --

    private fun handleSuspended(info: NdsDebugSession.SuspendInfo) {
        if (info.frames.isEmpty()) {
            log.warn("Suspended with no stack frames; reason=${info.reason}")
            return
        }
        val stack = NaturalExecutionStack(sessionRunner, locator, info.frames)
        val context = NaturalSuspendContext(stack)

        // Match the top stack frame to a registered breakpoint (if any).
        val top = info.frames.first()
        val matchedBp = registered[BpKey(top.library?.uppercase().orEmpty(), top.`object`?.uppercase().orEmpty(), top.line)]
        if (info.reason == NdsDebugSession.StopReason.BREAKPOINT && matchedBp != null) {
            session.breakpointReached(matchedBp, null, context)
        } else {
            session.positionReached(context)
        }
        if (info.reason == NdsDebugSession.StopReason.EXCEPTION && info.errorText != null) {
            consoleView.print("Exception: ${info.errorText}\n", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }

    private fun handleTerminated(reason: String?) {
        if (reason != null) consoleView.print("Terminated: $reason\n", ConsoleViewContentType.SYSTEM_OUTPUT)
        else consoleView.print("Natural session ended.\n", ConsoleViewContentType.SYSTEM_OUTPUT)
        session.stop()
    }

    private inline fun onPooled(crossinline block: () -> Unit) {
        ApplicationManager.getApplication().executeOnPooledThread {
            try { block() }
            catch (t: Throwable) {
                log.warn("Debug action failed", t)
                consoleView.print("Debug action failed: ${t.message}\n", ConsoleViewContentType.ERROR_OUTPUT)
            }
        }
    }

    private data class BpKey(val library: String, val obj: String, val line: Int)
}
