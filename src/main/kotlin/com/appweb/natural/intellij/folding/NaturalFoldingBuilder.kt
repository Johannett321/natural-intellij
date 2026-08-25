package com.appweb.natural.intellij.folding

import com.appweb.natural.intellij.psi.NaturalDecideForBlock
import com.appweb.natural.intellij.psi.NaturalDecideOnBlock
import com.appweb.natural.intellij.psi.NaturalDefineSubroutineBlock
import com.appweb.natural.intellij.psi.NaturalFindBlock
import com.appweb.natural.intellij.psi.NaturalForBlock
import com.appweb.natural.intellij.psi.NaturalIfBlock
import com.appweb.natural.intellij.psi.NaturalOnErrorBlock
import com.appweb.natural.intellij.psi.NaturalReadBlock
import com.appweb.natural.intellij.psi.NaturalRepeatBlock
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class NaturalFoldingBuilder : FoldingBuilderEx() {

    private val foldableTypes = listOf(
        NaturalDefineSubroutineBlock::class.java,
        NaturalIfBlock::class.java,
        NaturalForBlock::class.java,
        NaturalRepeatBlock::class.java,
        NaturalReadBlock::class.java,
        NaturalFindBlock::class.java,
        NaturalDecideForBlock::class.java,
        NaturalDecideOnBlock::class.java,
        NaturalOnErrorBlock::class.java,
    )

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> =
        foldableTypes
            .flatMap { PsiTreeUtil.findChildrenOfType(root, it) }
            .filter { it.textRange.length > 0 }
            .map { FoldingDescriptor(it.node, it.textRange) }
            .toTypedArray()

    override fun getPlaceholderText(node: ASTNode): String {
        val firstLine = node.text.lineSequence().firstOrNull { it.isNotBlank() }?.trim() ?: return "..."
        return "$firstLine ..."
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
