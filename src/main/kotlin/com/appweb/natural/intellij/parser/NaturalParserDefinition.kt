package com.appweb.natural.intellij.parser

import com.appweb.natural.intellij.lexer.NaturalLexerAdapter
import com.appweb.natural.intellij.lexer.NaturalTokenTypes
import com.appweb.natural.intellij.psi.NaturalElementType
import com.appweb.natural.intellij.psi.NaturalElementTypes
import com.appweb.natural.intellij.psi.NaturalFile
import com.appweb.natural.intellij.psi.NaturalTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class NaturalParserDefinition : ParserDefinition {
    override fun createLexer(p0: Project?): Lexer = NaturalLexerAdapter()

    override fun createParser(p0: Project?): PsiParser = NaturalParser()

    override fun getFileNodeType(): IFileElementType = NaturalElementTypes.FILE

    override fun getWhitespaceTokens(): TokenSet = NaturalTokenTypes.WHITESPACE

    override fun getCommentTokens(): TokenSet = NaturalTokenTypes.COMMENTS

    override fun getStringLiteralElements(): TokenSet = NaturalTokenTypes.STRING_LITERALS

    override fun createElement(node: ASTNode): PsiElement = NaturalTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = NaturalFile(viewProvider)


}