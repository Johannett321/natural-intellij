package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement

class NaturalRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?) =
        element is NaturalVariableDecl
}
