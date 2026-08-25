package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalPsiImplUtil
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class NaturalVariableReference(
    element: NaturalVariableRef,
    textRange: TextRange
) : PsiReferenceBase<NaturalVariableRef>(element, textRange) {

    private val name: String = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        val file = element.containingFile ?: return null
        // Search current file first
        PsiTreeUtil.findChildrenOfType(file, NaturalVariableDecl::class.java)
            .firstOrNull { NaturalPsiImplUtil.getName(it).equals(name, ignoreCase = true) }
            ?.let { return it }
        // Then search variables imported via LOCAL/GLOBAL/PARAMETER USING
        return NaturalDataAreaUtils.getImportedVariableDecls(file)
            .firstOrNull { NaturalPsiImplUtil.getName(it).equals(name, ignoreCase = true) }
    }

    override fun handleElementRename(newName: String): PsiElement {
        val nameNode = NaturalPsiImplUtil.nameNode(element.node) ?: return element
        NaturalPsiImplUtil.replaceNameToken(element.node, nameNode, newName, element.manager)
        return element
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
