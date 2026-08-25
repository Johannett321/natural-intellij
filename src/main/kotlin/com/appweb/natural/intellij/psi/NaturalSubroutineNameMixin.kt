package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.psi.references.SimpleNamedElementImpl
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class NaturalSubroutineNameMixin(node: ASTNode) : SimpleNamedElementImpl(node) {

    override fun getName(): String = NaturalPsiImplUtil.nameNode(node)?.text ?: ""

    override fun setName(newName: String): PsiElement {
        val nameNode = NaturalPsiImplUtil.nameNode(node) ?: return this
        NaturalPsiImplUtil.replaceNameToken(node, nameNode, newName, manager)
        return this
    }

    override fun getNameIdentifier(): PsiElement? = NaturalPsiImplUtil.nameNode(node)?.psi
}
