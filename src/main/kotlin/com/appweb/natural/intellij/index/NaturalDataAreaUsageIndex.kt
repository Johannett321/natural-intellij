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
 * Indexes LOCAL/GLOBAL/PARAMETER USING references in Natural files.
 *
 * Key:   uppercase data area name (e.g. "MYGLOB")
 * Value: type characters — "L" LOCAL USING, "G" GLOBAL USING, "P" PARAMETER USING
 *        (can be combined if the same name is used with different block types)
 *
 * Used by [com.appweb.natural.intellij.findusages.NaturalModuleFindUsagesHandlerFactory] to
 * find all files that import a given LDA, GDA, or PDA.
 */
class NaturalDataAreaUsageIndex : FileBasedIndexExtension<String, String>() {

    companion object {
        val NAME: ID<String, String> = ID.create("natural.dataarea.usage.index")
        const val LOCAL = "L"
        const val GLOBAL = "G"
        const val PARAMETER = "P"
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

        // Three-state scanner: waiting for scope keyword → waiting for USING → waiting for name
        var scopeKeyword: IElementType? = null
        var seenUsing = false

        while (lexer.tokenType != null) {
            val tokenType = lexer.tokenType!!
            if (tokenType != TokenType.WHITE_SPACE) {
                when {
                    // New scope keyword resets any in-progress scan
                    tokenType == NaturalTypes.KW_LOCAL ||
                    tokenType == NaturalTypes.KW_GLOBAL ||
                    tokenType == NaturalTypes.KW_PARAMETER -> {
                        scopeKeyword = tokenType
                        seenUsing = false
                    }
                    scopeKeyword != null && !seenUsing && tokenType == NaturalTypes.KW_USING -> {
                        seenUsing = true
                    }
                    scopeKeyword != null && seenUsing -> {
                        // Next non-whitespace after LOCAL/GLOBAL/PARAMETER USING is the data area name
                        if (tokenType == NaturalTypes.IDENTIFIER || tokenType == NaturalTypes.USER_VARIABLE) {
                            val name = lexer.tokenText.trim().uppercase()
                            if (name.isNotEmpty()) {
                                val typeChar = when (scopeKeyword) {
                                    NaturalTypes.KW_LOCAL -> LOCAL
                                    NaturalTypes.KW_GLOBAL -> GLOBAL
                                    NaturalTypes.KW_PARAMETER -> PARAMETER
                                    else -> null
                                }
                                if (typeChar != null) {
                                    val existing = result[name] ?: ""
                                    if (typeChar !in existing) result[name] = existing + typeChar
                                }
                            }
                        }
                        scopeKeyword = null
                        seenUsing = false
                    }
                    scopeKeyword != null && !seenUsing -> {
                        // Something other than KW_USING followed the scope keyword — not a USING block
                        scopeKeyword = null
                    }
                }
            }
            lexer.advance()
        }
        result
    }
}
