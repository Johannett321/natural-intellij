package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalPsiImplUtil
import com.appweb.natural.intellij.psi.NaturalSubroutineName
import com.appweb.natural.intellij.psi.NaturalSubroutineRef
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class NaturalSubroutineReference(
    element: NaturalSubroutineRef,
    textRange: TextRange
) : PsiReferenceBase<NaturalSubroutineRef>(element, textRange) {

    private val name: String = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        val file = element.containingFile ?: return null
        return PsiTreeUtil.findChildrenOfType(file, NaturalSubroutineName::class.java)
            .firstOrNull { it.name.equals(name, ignoreCase = true) }
    }

    override fun handleElementRename(newName: String): PsiElement {
        val nameNode = NaturalPsiImplUtil.nameNode(element.node) ?: return element
        NaturalPsiImplUtil.replaceNameToken(element.node, nameNode, newName, element.manager)
        return element
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
