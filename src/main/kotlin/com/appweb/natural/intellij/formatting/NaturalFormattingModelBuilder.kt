package com.appweb.natural.intellij.formatting

import com.appweb.natural.intellij.language.NaturalLanguage
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings

class NaturalFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val settings = formattingContext.codeStyleSettings
        val spacingBuilder = createSpacingBuilder(settings)
        val rootBlock = NaturalBlock(
            formattingContext.node,
            Indent.getNoneIndent(),
            null,
            null,
            spacingBuilder
        )
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            rootBlock,
            settings
        )
    }

    private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder =
        SpacingBuilder(settings, NaturalLanguage.INSTANCE)
}
