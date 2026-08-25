package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.lexer.NaturalLexerAdapter
import com.appweb.natural.intellij.lexer.NaturalTokenTypes
import com.appweb.natural.intellij.psi.NaturalSubroutineName
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet

class NaturalFindUsagesProvider : FindUsagesProvider {

    override fun getWordsScanner() = DefaultWordsScanner(
        NaturalLexerAdapter(),
        TokenSet.create(NaturalTypes.USER_VARIABLE, NaturalTypes.IDENTIFIER),
        NaturalTokenTypes.COMMENTS,
        NaturalTokenTypes.STRING_LITERALS
    )

    override fun canFindUsagesFor(element: PsiElement) =
        element is NaturalVariableDecl || element is NaturalSubroutineName

    override fun getHelpId(element: PsiElement): String? = null

    override fun getType(element: PsiElement) = when (element) {
        is NaturalSubroutineName -> "subroutine"
        else -> "variable"
    }

    override fun getDescriptiveName(element: PsiElement): String = when (element) {
        is NaturalSubroutineName -> element.name.orEmpty()
        is NaturalVariableDecl -> element.name ?: element.text
        else -> element.text
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String = when (element) {
        is NaturalSubroutineName -> element.name.orEmpty()
        is NaturalVariableDecl -> element.name ?: element.text
        else -> element.text
    }
}
