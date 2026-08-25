package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalSubroutineName
import com.appweb.natural.intellij.psi.NaturalSubroutineRef
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.rename.RenamePsiElementProcessor

class NaturalSubroutineRenamePsiElementProcessor : RenamePsiElementProcessor() {

    override fun canProcessElement(element: PsiElement) = element is NaturalSubroutineName

    override fun findReferences(
        element: PsiElement,
        searchScope: SearchScope,
        searchInCommentsAndStrings: Boolean
    ): Collection<PsiReference> {
        if (element !is NaturalSubroutineName) return emptyList()
        val file = element.containingFile ?: return emptyList()
        return PsiTreeUtil.findChildrenOfType(file, NaturalSubroutineRef::class.java)
            .mapNotNull { it.reference }
            .filter { it.resolve() == element }
    }
}
