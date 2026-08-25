package com.appweb.natural.intellij.debug

import com.intellij.icons.AllIcons
import com.intellij.xdebugger.frame.XExecutionStack
import com.intellij.xdebugger.frame.XStackFrame
import com.softwareag.naturalone.natural.pal.PalTypeDbgStackFrame

class NaturalExecutionStack(
    private val session: NdsDebugSession,
    private val locator: NaturalSourceLocator,
    private val frames: List<PalTypeDbgStackFrame>,
) : XExecutionStack("Natural", AllIcons.Debugger.ThreadCurrent) {

    private val xFrames: List<XStackFrame> by lazy {
        frames.map { NaturalStackFrame(session, locator, it) }
    }

    override fun getTopFrame(): XStackFrame? = xFrames.firstOrNull()

    override fun computeStackFrames(firstFrameIndex: Int, container: XStackFrameContainer) {
        if (firstFrameIndex >= xFrames.size) {
            container.addStackFrames(emptyList(), true)
        } else {
            container.addStackFrames(xFrames.subList(firstFrameIndex, xFrames.size), true)
        }
    }
}

class NaturalSuspendContext(
    private val stack: NaturalExecutionStack,
) : com.intellij.xdebugger.frame.XSuspendContext() {
    override fun getActiveExecutionStack() = stack
    override fun getExecutionStacks() = arrayOf<XExecutionStack>(stack)
}
