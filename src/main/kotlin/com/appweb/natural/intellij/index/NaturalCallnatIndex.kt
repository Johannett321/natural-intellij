package com.appweb.natural.intellij.index

import com.appweb.natural.intellij.language.NaturalFileType
import com.appweb.natural.intellij.lexer.NaturalLexerAdapter
import com.appweb.natural.intellij.psi.NaturalTypes
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.FileContent
import com.intellij.util.indexing.ID
import com.intellij.util.io.EnumeratorStringDescriptor

/**
 * Indexes CALLNAT and INCLUDE references in Natural files.
 *
 * Key:   uppercase module name (e.g. "MYSUBPROG")
 * Value: type characters — "C" if referenced via CALLNAT, "I" via INCLUDE (can be combined, e.g. "CI")
 *
 * Used by [com.appweb.natural.intellij.findusages.NaturalModuleFindUsagesHandlerFactory] to
 * find all files that call a given subprogram or include a given copycode.
 */
class NaturalCallnatIndex : FileBasedIndexExtension<String, String>() {

    companion object {
        val NAME: ID<String, String> = ID.create("natural.callnat.index")
        const val CALLNAT = "C"
        const val INCLUDE = "I"
    }

    override fun getName() = NAME
    override fun getVersion() = 1
    override fun dependsOnFileContent() = true
    override fun getKeyDescriptor(): EnumeratorStringDescriptor = EnumeratorStringDescriptor.INSTANCE
    override fun getValueExternalizer(): EnumeratorStringDescriptor = EnumeratorStringDescriptor.INSTANCE

    override fun getInputFilter() = FileBasedIndex.InputFilter { file ->
        file.fileType is NaturalFileType
    }

    override fun getIndexer() = DataIndexer<String, String, FileContent> { fileContent ->
        val result = mutableMapOf<String, String>()
        val lexer = NaturalLexerAdapter()
        lexer.start(fileContent.contentAsText)

        var prevKeyword: IElementType? = null
        while (lexer.tokenType != null) {
            val tokenType = lexer.tokenType!!
            if (tokenType != TokenType.WHITE_SPACE) {
                when {
                    tokenType == NaturalTypes.KW_CALLNAT || tokenType == NaturalTypes.KW_INCLUDE -> {
                        prevKeyword = tokenType
                    }
                    prevKeyword != null && isNameToken(tokenType) -> {
                        val name = lexer.tokenText.trim()
                            .removeSurrounding("'").removeSurrounding("\"").uppercase()
                        if (name.isNotEmpty()) {
                            val typeChar = if (prevKeyword == NaturalTypes.KW_CALLNAT) CALLNAT else INCLUDE
                            val existing = result[name] ?: ""
                            if (typeChar !in existing) result[name] = existing + typeChar
                        }
                        prevKeyword = null
                    }
                    else -> prevKeyword = null
                }
            }
            lexer.advance()
        }
        result
    }

    private fun isNameToken(type: IElementType) =
        type == NaturalTypes.IDENTIFIER ||
        type == NaturalTypes.USER_VARIABLE ||
        type == NaturalTypes.STRING_LITERAL
}
