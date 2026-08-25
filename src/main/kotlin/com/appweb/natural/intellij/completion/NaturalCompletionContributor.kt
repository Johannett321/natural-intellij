package com.appweb.natural.intellij.completion

import com.appweb.natural.intellij.completion.keywordproviders.NaturalDataSectionKeywordProvider
import com.appweb.natural.intellij.completion.keywordproviders.NaturalEscapeCompletionProvider
import com.appweb.natural.intellij.completion.keywordproviders.NaturalKeywordCompletionProvider
import com.appweb.natural.intellij.completion.keywordproviders.NaturalSnippetCompletionProvider
import com.appweb.natural.intellij.completion.keywordproviders.NaturalVariableCompletionProvider
import com.appweb.natural.intellij.language.NaturalLanguage
import com.appweb.natural.intellij.lexer.NaturalTokenTypes
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns

class NaturalCompletionContributor : CompletionContributor() {
    init {
        val anyNatural = PlatformPatterns.psiElement().withLanguage(NaturalLanguage.INSTANCE)
        extend(CompletionType.BASIC, anyNatural, NaturalKeywordCompletionProvider())
        extend(CompletionType.BASIC, anyNatural, NaturalDataSectionKeywordProvider())
        extend(CompletionType.BASIC, anyNatural, NaturalVariableCompletionProvider())
        extend(CompletionType.BASIC, anyNatural, NaturalSnippetCompletionProvider())
        extend(CompletionType.BASIC, anyNatural, NaturalEscapeCompletionProvider())
    }

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        if (parameters.position.node.elementType in NaturalTokenTypes.COMMENTS) return
        super.fillCompletionVariants(parameters, result)
    }
}
