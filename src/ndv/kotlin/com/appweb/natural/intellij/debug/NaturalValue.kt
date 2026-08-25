package com.appweb.natural.intellij.debug

import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.xdebugger.frame.XCompositeNode
import com.intellij.xdebugger.frame.XValue
import com.intellij.xdebugger.frame.XValueChildrenList
import com.intellij.xdebugger.frame.XValueGroup
import com.intellij.xdebugger.frame.XValueNode
import com.intellij.xdebugger.frame.XValuePlace
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgSyt
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgVarContainer
import com.softwareag.naturalone.natural.pal.external.PalTypeDbgVarDescFactory

/**
 * One scope (Local/Global/AIV/Context/System) under a stack frame. Children are produced by
 * looking up the symbol table for the scope on demand.
 */
class NaturalScopeGroup(
    private val session: NdsDebugSession,
    private val frame: com.softwareag.naturalone.natural.pal.PalTypeDbgStackFrame,
    private val scope: NdsDebugSession.VarScope,
    name: String,
) : XValueGroup(name) {

    override fun isAutoExpand(): Boolean = scope == NdsDebugSession.VarScope.LOCAL

    override fun computeChildren(node: XCompositeNode) {
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val table = session.getSymbolTable(frame, scope)
                val list = XValueChildrenList()
                // Only top-level (level 1) entries are shown directly. Nested fields are
                // discovered lazily when the parent group is expanded.
                table.entries.forEachIndexed { idx, syt ->
                    if (syt.level == 1) {
                        list.add(syt.name ?: "?", NaturalValue(session, table.container, table.entries, idx))
                    }
                }
                node.addChildren(list, true)
            } catch (t: Throwable) {
                node.setErrorMessage(t.message ?: t.javaClass.simpleName)
            }
        }
    }
}

/**
 * One Natural variable shown in the Variables panel.
 *
 * Scalars compute their value immediately. Groups expand into their level+1 children
 * (the entries that follow them in the symbol table at exactly one deeper level). Arrays
 * surface each occurrence as an indexed child.
 */
class NaturalValue(
    private val session: NdsDebugSession,
    private val container: IPalTypeDbgVarContainer,
    private val symbols: List<IPalTypeDbgSyt>,
    private val index: Int,
) : XValue() {

    private val syt: IPalTypeDbgSyt = symbols[index]

    override fun computePresentation(node: XValueNode, place: XValuePlace) {
        val typeLabel = formatType(syt)
        val expandable = hasChildren()
        if (syt.isGroup) {
            node.setPresentation(AllIcons.Debugger.Value, typeLabel, "", true)
            return
        }
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val desc = PalTypeDbgVarDescFactory.newInstance(syt, syt.indices)
                val values = session.getValue(container, desc)
                val text = values.firstOrNull()?.value?.trim() ?: ""
                node.setPresentation(AllIcons.Debugger.Value, typeLabel, text, expandable)
            } catch (t: Throwable) {
                node.setPresentation(AllIcons.Debugger.Value, typeLabel, "<err: ${t.message}>", false)
            }
        }
    }

    override fun computeChildren(node: XCompositeNode) {
        val list = XValueChildrenList()
        if (syt.isGroup) {
            // Find children at level = ours + 1, scanning forward until we see a sibling at
            // our level or shallower.
            val ourLevel = syt.level
            var i = index + 1
            while (i < symbols.size && symbols[i].level > ourLevel) {
                if (symbols[i].level == ourLevel + 1) {
                    list.add(symbols[i].name ?: "?", NaturalValue(session, container, symbols, i))
                }
                i++
            }
            node.addChildren(list, true)
            return
        }
        // Non-group: if it's an array or has multiple occurrences, surface each as an index.
        if (syt.isVarray || syt.isXarray || syt.numberOfElements > 1) {
            ApplicationManager.getApplication().executeOnPooledThread {
                try {
                    val desc = PalTypeDbgVarDescFactory.newInstance(syt, syt.indices)
                    val values = session.getValue(container, desc)
                    values.forEachIndexed { idx, v ->
                        list.add("[${idx + 1}]", LeafValue(v.value ?: ""))
                    }
                    node.addChildren(list, true)
                } catch (t: Throwable) {
                    node.setErrorMessage(t.message ?: t.javaClass.simpleName)
                }
            }
            return
        }
        node.addChildren(list, true)
    }

    private fun hasChildren(): Boolean {
        if (syt.isGroup) return true
        if (syt.isVarray || syt.isXarray) return true
        if (syt.numberOfElements > 1) return true
        return false
    }

    private class LeafValue(private val text: String) : XValue() {
        override fun computePresentation(node: XValueNode, place: XValuePlace) {
            node.setPresentation(AllIcons.Debugger.Value, null, text, false)
        }
    }

    private fun formatType(syt: IPalTypeDbgSyt): String {
        val format = when (syt.format) {
            IPalTypeDbgSyt.OPT_ALPHA -> "A"
            IPalTypeDbgSyt.OPT_NUM   -> "N"
            IPalTypeDbgSyt.OPT_PACK  -> "P"
            IPalTypeDbgSyt.OPT_INT   -> "I"
            IPalTypeDbgSyt.OPT_FLOAT -> "F"
            IPalTypeDbgSyt.OPT_BIN   -> "B"
            IPalTypeDbgSyt.OPT_DATE  -> "D"
            IPalTypeDbgSyt.OPT_TIME  -> "T"
            IPalTypeDbgSyt.OPT_LOG   -> "L"
            IPalTypeDbgSyt.OPT_UNICODE -> "U"
            else -> "?"
        }
        val len = syt.length
        return when {
            syt.isGroup -> "GROUP"
            syt.precision > 0 -> "$format$len.${syt.precision}"
            else -> "$format$len"
        }
    }
}
