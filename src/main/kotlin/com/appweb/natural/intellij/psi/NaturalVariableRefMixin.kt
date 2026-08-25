package com.appweb.natural.intellij.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference

abstract class NaturalVariableRefMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getReference(): PsiReference? = NaturalPsiImplUtil.getReference(this as NaturalVariableRef)
}
