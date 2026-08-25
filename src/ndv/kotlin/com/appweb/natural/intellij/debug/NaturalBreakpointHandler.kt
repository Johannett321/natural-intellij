package com.appweb.natural.intellij.debug

import com.intellij.xdebugger.breakpoints.XBreakpointProperties
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import com.intellij.xdebugger.breakpoints.XBreakpointHandler

class NaturalBreakpointHandler(
    private val process: NaturalDebugProcess,
) : XBreakpointHandler<XLineBreakpoint<XBreakpointProperties<*>>>(NaturalLineBreakpointType::class.java) {

    override fun registerBreakpoint(breakpoint: XLineBreakpoint<XBreakpointProperties<*>>) {
        process.registerBreakpoint(breakpoint)
    }

    override fun unregisterBreakpoint(
        breakpoint: XLineBreakpoint<XBreakpointProperties<*>>,
        temporary: Boolean,
    ) {
        process.unregisterBreakpoint(breakpoint)
    }
}
