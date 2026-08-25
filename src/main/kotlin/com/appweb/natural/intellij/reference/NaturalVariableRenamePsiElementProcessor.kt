package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalPsiImplUtil
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.rename.RenamePsiElementProcessor

class NaturalVariableRenamePsiElementProcessor : RenamePsiElementProcessor() {

    override fun canProcessElement(element: PsiElement) = element is NaturalVariableDecl

    override fun findReferences(
        element: PsiElement,
        searchScope: SearchScope,
        searchInCommentsAndStrings: Boolean
    ): Collection<PsiReference> {
        if (element !is NaturalVariableDecl) return emptyList()
        val name = NaturalPsiImplUtil.getName(element) ?: return emptyList()
        val file = element.containingFile ?: return emptyList()
        return PsiTreeUtil.findChildrenOfType(file, NaturalVariableRef::class.java)
            .mapNotNull { it.reference }
            .filter { it.resolve() == element }
    }
}
