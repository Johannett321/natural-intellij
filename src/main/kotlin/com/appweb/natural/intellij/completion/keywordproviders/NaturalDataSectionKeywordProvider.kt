package com.appweb.natural.intellij.completion.keywordproviders

import com.appweb.natural.intellij.completion.isInsideDefineDataBlock
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import java.util.Locale
import java.util.Locale.getDefault

class NaturalDataSectionKeywordProvider : CompletionProvider<CompletionParameters>() {

    val knownWords = arrayOf<String> ("LOCAL", "PARAMETER", "USING")

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        if (!isInsideDefineDataBlock(parameters)) return

        val textBefore = parameters.originalFile.text.substring(0, parameters.offset).lines().last().trimEnd()
        val lastWord = textBefore.split(Regex("\\s+")).lastOrNull()?.uppercase()
        val numWordsOnLine = textBefore.split(Regex("\\s+")).size

        if (lastWord == "LOCAL" || lastWord == "PARAMETER") {
            result.addElement(LookupElementBuilder.create("USING"))
        }else if (numWordsOnLine > 2 && !knownWords.contains(lastWord?.uppercase(getDefault()))){
            result.addElement(LookupElementBuilder.create("INIT"))
            result.addElement(LookupElementBuilder.create("DYNAMIC"))
            result.addElement(LookupElementBuilder.create("REDEFINE"))
        }else if (isInteger(textBefore.replace(" ", ""))) {
            return // return early. User is typing in a variable name
        } else {
            result.addElement(LookupElementBuilder.create("LOCAL"))
            result.addElement(LookupElementBuilder.create("PARAMETER"))
        }
    }

    private fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false
}