package com.appweb.natural.intellij.highlighting

import com.appweb.natural.intellij.lexer.NaturalLexerAdapter
import com.appweb.natural.intellij.lexer.NaturalTokenTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import java.awt.Color

class NaturalSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        val KEYWORD: TextAttributesKey = createTextAttributesKey(
            "NATURAL_KEYWORD", TextAttributes().apply {
                foregroundColor = Color(236, 149, 236) // purple
            }
        )

        val MODIFIERS: TextAttributesKey = createTextAttributesKey(
            "NATURAL_MODIFIERS", TextAttributes().apply {
                foregroundColor = Color(138, 172, 251) // blue
            }
        )

        val LOGICAL: TextAttributesKey = createTextAttributesKey(
            "NATURAL_LOGICAL", TextAttributes().apply {
                foregroundColor = Color(138, 172, 251) // blue
            }
        )

        val DATA_TYPES: TextAttributesKey = createTextAttributesKey(
            "NATURAL_DATA_TYPES", TextAttributes().apply {
                foregroundColor = Color(255, 198, 167) // blue
            }
        )

        val SESSION_PARAMS: TextAttributesKey = createTextAttributesKey(
            "NATURAL_SESSION_PARAMS", TextAttributes().apply {
                foregroundColor = Color(138, 172, 251) // blue
            }
        )

        val SYSTEM_VARIABLES: TextAttributesKey = createTextAttributesKey(
            "NATURAL_SYSTEM_VARIABLES", TextAttributes().apply {
                foregroundColor = Color(190, 87, 241) // purple
            }
        )

        val LINE_COMMENT: TextAttributesKey = createTextAttributesKey(
            "NATURAL_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        val STRING: TextAttributesKey = createTextAttributesKey(
            "NATURAL_STRING", DefaultLanguageHighlighterColors.STRING
        )

        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val MODIFIER_KEYS = arrayOf(MODIFIERS)
        private val LOGICAL_KEYS = arrayOf(LOGICAL)
        private val DATA_TYPE_KEYS = arrayOf(DATA_TYPES)
        private val SESSION_PARAM_KEYS = arrayOf(SESSION_PARAMS)
        private val SYSTEM_VARIABLE_KEYS = arrayOf(SYSTEM_VARIABLES)
        private val LINE_CMT_KEYS = arrayOf(LINE_COMMENT)
        private val STRING_KEYS = arrayOf(STRING)
    }

    override fun getHighlightingLexer(): Lexer = NaturalLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType) : Array<TextAttributesKey> = when {
        tokenType in NaturalTokenTypes.KEYWORDS -> KEYWORD_KEYS
        tokenType in NaturalTokenTypes.MODIFIERS -> MODIFIER_KEYS
        tokenType in NaturalTokenTypes.LOGICAL -> LOGICAL_KEYS
        tokenType in NaturalTokenTypes.DATA_TYPES -> DATA_TYPE_KEYS
        tokenType in NaturalTokenTypes.SESSION_PARAMS -> SESSION_PARAM_KEYS
        tokenType in NaturalTokenTypes.SYSTEM_VARIABLES -> SYSTEM_VARIABLE_KEYS
        tokenType == NaturalTokenTypes.LINE_COMMENT -> LINE_CMT_KEYS
        tokenType == NaturalTokenTypes.STRING_LITERAL -> STRING_KEYS
        else -> EMPTY
    }
}