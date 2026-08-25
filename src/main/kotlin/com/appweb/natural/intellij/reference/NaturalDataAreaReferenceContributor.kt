package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.util.ProcessingContext

/**
 * Registers a PSI reference on the identifier in `LOCAL/GLOBAL/PARAMETER USING <name>`,
 * enabling Ctrl+Click to navigate to the referenced data area file.
 */
class NaturalDataAreaReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement().withParent(NaturalDataAreaBlock::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    val block = element.parent as? NaturalDataAreaBlock ?: return PsiReference.EMPTY_ARRAY
                    val usingIdentifier = NaturalDataAreaUtils.getUsingIdentifier(block) ?: return PsiReference.EMPTY_ARRAY
                    if (usingIdentifier != element) return PsiReference.EMPTY_ARRAY
                    return arrayOf(NaturalDataAreaReference(element, TextRange(0, element.textLength)))
                }
            }
        )
    }
}
