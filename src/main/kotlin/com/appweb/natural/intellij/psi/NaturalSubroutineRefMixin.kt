package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.reference.NaturalSubroutineReference
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiReference

abstract class NaturalSubroutineRefMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getReference(): PsiReference? {
        val nameNode = NaturalPsiImplUtil.nameNode(node) ?: return null
        val start = nameNode.startOffsetInParent
        return NaturalSubroutineReference(
            this as NaturalSubroutineRef,
            TextRange(start, start + nameNode.textLength)
        )
    }
}
