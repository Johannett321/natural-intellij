package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase

/**
 * A PSI reference for the identifier in a `LOCAL/GLOBAL/PARAMETER USING <name>` declaration.
 * Resolves to the corresponding data area file (NSL, NSG, or NPD).
 */
class NaturalDataAreaReference(element: PsiElement, textRange: TextRange)
    : PsiReferenceBase<PsiElement>(element, textRange) {

    override fun resolve(): PsiElement? {
        val block = element.parent as? NaturalDataAreaBlock ?: return null
        return NaturalDataAreaUtils.resolveDataAreaFile(block)
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
