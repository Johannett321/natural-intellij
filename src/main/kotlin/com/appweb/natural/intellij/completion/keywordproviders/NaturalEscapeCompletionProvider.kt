package com.appweb.natural.intellij.completion.keywordproviders

import com.appweb.natural.intellij.completion.isAfterEscapeDirection
import com.appweb.natural.intellij.completion.isAfterEscapeKeyword
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class NaturalEscapeCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        when {
            isAfterEscapeDirection(parameters) -> {
                result.addElement(LookupElementBuilder.create("IMMEDIATE"))
            }
            isAfterEscapeKeyword(parameters) -> {
                result.addElement(LookupElementBuilder.create("BOTTOM"))
                result.addElement(LookupElementBuilder.create("TOP"))
                result.addElement(LookupElementBuilder.create("ROUTINE"))
                result.addElement(LookupElementBuilder.create("MODULE"))
                result.addElement(LookupElementBuilder.create("IMMEDIATE"))
            }
        }
    }
}
