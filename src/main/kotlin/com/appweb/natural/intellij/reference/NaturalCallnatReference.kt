package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalCallnatStatement
import com.appweb.natural.intellij.psi.NaturalIncludeStatement
import com.appweb.natural.intellij.psi.NaturalTypes
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.psi.TokenType
import com.intellij.util.ProcessingContext

/** Reference on the subprogram-name token in a CALLNAT statement → resolves to the .NSN file. */
class NaturalCallnatReference(element: PsiElement, textRange: TextRange)
    : PsiReferenceBase<PsiElement>(element, textRange) {

    override fun resolve(): PsiElement? {
        val name = element.text
            .substring(rangeInElement.startOffset, rangeInElement.endOffset)
            .trim().removeSurrounding("'").removeSurrounding("\"")
        return NaturalDataAreaUtils.resolveSubprogram(name, element.project, element.containingFile)
    }

    override fun getVariants(): Array<Any> = emptyArray()
}

/** Reference on the copycode-name token in an INCLUDE statement → resolves to the .NSC file. */
class NaturalIncludeReference(element: PsiElement, textRange: TextRange)
    : PsiReferenceBase<PsiElement>(element, textRange) {

    override fun resolve(): PsiElement? {
        val name = element.text.substring(rangeInElement.startOffset, rangeInElement.endOffset)
        return NaturalDataAreaUtils.resolveCopyCode(name, element.project, element.containingFile)
    }

    override fun getVariants(): Array<Any> = emptyArray()
}

/** Registers CALLNAT name references for both string-literal and bare-identifier forms. */
class NaturalCallnatReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        // CALLNAT 'NAME' or CALLNAT NAME
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement().withParent(NaturalCallnatStatement::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    var prev = element.node.treePrev
                    while (prev != null && prev.elementType == TokenType.WHITE_SPACE) prev = prev.treePrev
                    if (prev?.elementType != NaturalTypes.KW_CALLNAT) return PsiReference.EMPTY_ARRAY
                    val text = element.text
                    val range = if (text.startsWith("'") || text.startsWith("\""))
                        TextRange(1, text.length - 1) else TextRange(0, text.length)
                    return arrayOf(NaturalCallnatReference(element, range))
                }
            }
        )

        // INCLUDE NAME
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement().withParent(NaturalIncludeStatement::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    var prev = element.node.treePrev
                    while (prev != null && prev.elementType == TokenType.WHITE_SPACE) prev = prev.treePrev
                    if (prev?.elementType != NaturalTypes.KW_INCLUDE) return PsiReference.EMPTY_ARRAY
                    return arrayOf(NaturalIncludeReference(element, TextRange(0, element.textLength)))
                }
            }
        )
    }
}
