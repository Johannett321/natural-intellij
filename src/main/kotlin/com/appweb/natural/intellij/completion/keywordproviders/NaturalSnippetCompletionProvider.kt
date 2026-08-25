package com.appweb.natural.intellij.completion.keywordproviders

import com.appweb.natural.intellij.completion.NaturalTemplateInsertHandler
import com.appweb.natural.intellij.completion.isAfterEscapeDirection
import com.appweb.natural.intellij.completion.isAfterEscapeKeyword
import com.appweb.natural.intellij.completion.isInsideDefineDataBlock
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class NaturalSnippetCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        if (isInsideDefineDataBlock(parameters)) return
        if (isAfterEscapeKeyword(parameters) || isAfterEscapeDirection(parameters)) return

        result.addElement(
            LookupElementBuilder.create("IF")
                .withTypeText("snippet")
                .withTailText(" <CONDITION> ... END-IF", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "IF \$CONDITION\$\n  \$CODE\$\nEND-IF\$END\$"
                    )
                )
        )

        result.addElement(
            LookupElementBuilder.create("IF/ELSE")
                .withTypeText("snippet")
                .withTailText(" <CONDITION> ... ELSE ... END-IF", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "IF \$CONDITION\$\n  \$CODE\$\nELSE\n  \$ELSE_CODE\$\nEND-IF\$END\$"
                    )
                )
        )

        result.addElement(
            LookupElementBuilder.create("FOR")
                .withTypeText("snippet")
                .withTailText(" <VAR> = <FROM> TO <TO> ... END-FOR", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "FOR \$VAR\$ = \$FROM\$ TO \$TO\$\n  \$CODE\$\nEND-FOR\$END\$"
                    )
                )
        )

        result.addElement(
            LookupElementBuilder.create("REPEAT")
                .withTypeText("snippet")
                .withTailText(" ... UNTIL <CONDITION>", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "REPEAT\n  \$CODE\$\nUNTIL \$CONDITION\$\$END\$"
                    )
                )
        )

        result.addElement(
            LookupElementBuilder.create("DECIDE FOR")
                .withTypeText("snippet")
                .withTailText(" FIRST/EVERY ... END-DECIDE", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "DECIDE FOR FIRST CONDITION\n  WHEN \$CONDITION\$\n    \$CODE\$\n  WHEN NONE\n    \$NONE_CODE\$\nEND-DECIDE\$END\$"
                    )
                )
        )

        result.addElement(
            LookupElementBuilder.create("DEFINE SUBROUTINE")
                .withTypeText("snippet")
                .withTailText(" <NAME> ... END-SUBROUTINE", true)
                .withInsertHandler(
                    NaturalTemplateInsertHandler(
                        "************************************************************************\n" +
                        "DEFINE SUBROUTINE \$NAME\$\n" +
                        "************************************************************************\n" +
                        "  \$CODE\$\n" +
                        "END-SUBROUTINE\$END\$",
                        reformat = false
                    )
                )
        )
    }
}
