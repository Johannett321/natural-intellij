package com.appweb.natural.intellij.structure

import com.appweb.natural.intellij.NaturalIcons
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalDefineDataPhase
import com.appweb.natural.intellij.psi.NaturalDefineSubroutineBlock
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.icons.AllIcons
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import javax.swing.Icon

class NaturalStructureViewElement(private val element: com.intellij.psi.PsiElement) : StructureViewTreeElement {

    override fun getValue() = element

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String? = when (element) {
            is PsiFile -> element.name
            is NaturalDefineDataPhase -> "DEFINE DATA"
            is NaturalDefineSubroutineBlock -> element.subroutineName?.name ?: "SUBROUTINE"
            is NaturalDataAreaBlock -> {
                val usingId = NaturalDataAreaUtils.getUsingIdentifier(element)
                val scope = element.node.firstChildNode?.text ?: "?"
                if (usingId != null) "$scope USING ${usingId.text}" else scope
            }
            is NaturalVariableDecl -> element.name
            else -> null
        }

        override fun getIcon(unused: Boolean): Icon? = when (element) {
            is NaturalDefineSubroutineBlock -> NaturalIcons.SUBROUTINE
            is NaturalDataAreaBlock -> when (element.node.firstChildNode?.elementType) {
                NaturalTypes.KW_LOCAL -> NaturalIcons.LDA
                NaturalTypes.KW_GLOBAL -> NaturalIcons.GDA
                NaturalTypes.KW_PARAMETER -> NaturalIcons.PDA
                else -> AllIcons.Nodes.Package
            }
            is NaturalVariableDecl -> AllIcons.Nodes.Variable
            else -> null
        }

        override fun getLocationString(): String? = when (element) {
            is NaturalVariableDecl -> element.dataType?.text
            is NaturalDataAreaBlock -> {
                val usingId = NaturalDataAreaUtils.getUsingIdentifier(element)
                if (usingId != null) {
                    val resolved = NaturalDataAreaUtils.resolveDataAreaFile(element)
                    resolved?.name
                } else null
            }
            else -> null
        }
    }

    override fun getChildren(): Array<TreeElement> = when (element) {
        is PsiFile -> buildList {
            PsiTreeUtil.findChildOfType(element, NaturalDefineDataPhase::class.java)
                ?.let { add(NaturalStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(element, NaturalDefineSubroutineBlock::class.java)
                .forEach { add(NaturalStructureViewElement(it)) }
        }.toTypedArray()

        is NaturalDefineDataPhase ->
            PsiTreeUtil.findChildrenOfType(element, NaturalDataAreaBlock::class.java)
                .flatMap { block ->
                    val usingId = NaturalDataAreaUtils.getUsingIdentifier(block)
                    if (usingId != null) {
                        // USING block: show it as a collapsible node
                        listOf(NaturalStructureViewElement(block))
                    } else {
                        // Inline variable declarations: show each variable directly
                        block.variableDeclList.map { NaturalStructureViewElement(it) }
                    }
                }.toTypedArray()

        is NaturalDataAreaBlock -> {
            // Expand a USING block to show the variables from the referenced file
            NaturalDataAreaUtils.resolveDataAreaFile(element)
                ?.let { PsiTreeUtil.findChildrenOfType(it, NaturalVariableDecl::class.java) }
                .orEmpty()
                .map { NaturalStructureViewElement(it) }
                .toTypedArray()
        }

        else -> emptyArray()
    }

    override fun navigate(requestFocus: Boolean) = (element as? NavigationItem)?.navigate(requestFocus) ?: Unit
    override fun canNavigate() = (element as? NavigationItem)?.canNavigate() ?: false
    override fun canNavigateToSource() = (element as? NavigationItem)?.canNavigateToSource() ?: false
}
