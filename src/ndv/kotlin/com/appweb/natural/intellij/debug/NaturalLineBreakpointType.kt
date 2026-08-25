package com.appweb.natural.intellij.debug

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.xdebugger.breakpoints.XLineBreakpointType
import com.intellij.xdebugger.breakpoints.XBreakpointProperties

/**
 * Line breakpoint type for Natural source files. We support breakpoints in any module that the
 * NDV runtime can execute — programs, subprograms, subroutines and helproutines. Maps, DDMs, LDAs,
 * PDAs and GDAs are not directly executable so we exclude them.
 */
class NaturalLineBreakpointType : XLineBreakpointType<XBreakpointProperties<*>>(
    "natural-line", "Natural Line Breakpoints",
) {
    override fun canPutAt(file: VirtualFile, line: Int, project: Project): Boolean {
        val ext = file.extension?.uppercase() ?: return false
        return ext in EXECUTABLE_EXTENSIONS
    }

    override fun createBreakpointProperties(file: VirtualFile, line: Int): XBreakpointProperties<*>? = null

    companion object {
        // Extensions whose runtime supports line-level breakpoints via spySet.
        val EXECUTABLE_EXTENSIONS = setOf("NSP", "NSN", "NSS", "NSC", "NSH")
    }
}
