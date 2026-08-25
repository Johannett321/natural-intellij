package com.appweb.natural.intellij.completion.keywordproviders

import com.appweb.natural.intellij.completion.isAfterEscapeDirection
import com.appweb.natural.intellij.completion.isAfterEscapeKeyword
import com.appweb.natural.intellij.completion.isInsideDefineDataBlock
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class NaturalVariableCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        if (isInsideDefineDataBlock(parameters)) return
        if (isAfterEscapeKeyword(parameters) || isAfterEscapeDirection(parameters)) return
        val file = parameters.originalFile
        PsiTreeUtil.findChildrenOfType(file, NaturalVariableDecl::class.java)
            .mapNotNull { it.name }
            .distinct()
            .forEach { name ->
                result.addElement(
                    LookupElementBuilder.create(name)
                        .withIcon(AllIcons.Nodes.Variable)
                )
            }
    }
}
