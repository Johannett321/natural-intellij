package com.appweb.natural.intellij.debug

import com.intellij.icons.AllIcons
import com.intellij.ui.ColoredTextContainer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.frame.XCompositeNode
import com.intellij.xdebugger.frame.XStackFrame
import com.intellij.xdebugger.frame.XValueChildrenList
import com.softwareag.naturalone.natural.pal.PalTypeDbgStackFrame

class NaturalStackFrame(
    private val session: NdsDebugSession,
    private val locator: NaturalSourceLocator,
    val frame: PalTypeDbgStackFrame,
) : XStackFrame() {

    override fun getSourcePosition(): XSourcePosition? =
        locator.sourcePosition(frame.library, frame.`object`, frame.line)

    override fun customizePresentation(component: ColoredTextContainer) {
        val obj = frame.`object` ?: "?"
        val lib = frame.library ?: "?"
        component.append("$obj", SimpleTextAttributes.REGULAR_ATTRIBUTES)
        component.append("  (", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        component.append("$lib:${frame.line}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        component.append(")", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        component.setIcon(AllIcons.Debugger.Frame)
    }

    override fun computeChildren(node: XCompositeNode) {
        val list = XValueChildrenList()
        list.addTopGroup(NaturalScopeGroup(session, frame, NdsDebugSession.VarScope.LOCAL, "Local"))
        list.addTopGroup(NaturalScopeGroup(session, frame, NdsDebugSession.VarScope.GLOBAL, "Global"))
        list.addTopGroup(NaturalScopeGroup(session, frame, NdsDebugSession.VarScope.AIV, "Application Independent"))
        list.addTopGroup(NaturalScopeGroup(session, frame, NdsDebugSession.VarScope.SYSTEM, "System Variables"))
        node.addChildren(list, true)
    }
}
