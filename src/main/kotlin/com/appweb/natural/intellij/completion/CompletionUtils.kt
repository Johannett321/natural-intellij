package com.appweb.natural.intellij.completion

import com.appweb.natural.intellij.lexer.NaturalTokenTypes
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.psi.util.PsiTreeUtil

fun isInsideDefineDataBlock(parameters: CompletionParameters): Boolean {
    val text = parameters.originalFile.text.uppercase()
    val offset = minOf(parameters.offset, text.length)
    val before = text.substring(0, offset)
    val lastDefineData = before.lastIndexOf("DEFINE DATA")
    val lastEndDefine = before.lastIndexOf("END-DEFINE")
    return lastDefineData >= 0 && lastDefineData > lastEndDefine
}

private fun prevLeafType(parameters: CompletionParameters) =
    PsiTreeUtil.prevVisibleLeaf(parameters.position)?.node?.elementType

private fun prevPrevLeafType(parameters: CompletionParameters) =
    PsiTreeUtil.prevVisibleLeaf(parameters.position)
        ?.let { PsiTreeUtil.prevVisibleLeaf(it)?.node?.elementType }

fun isAfterEscapeKeyword(parameters: CompletionParameters): Boolean =
    prevLeafType(parameters) == NaturalTokenTypes.KW_ESCAPE

private val escapeDirectionTypes = setOf(
    NaturalTokenTypes.KW_BOTTOM,
    NaturalTokenTypes.KW_TOP,
    NaturalTokenTypes.KW_ROUTINE,
    NaturalTokenTypes.KW_MODULE
)

fun isAfterEscapeDirection(parameters: CompletionParameters): Boolean =
    prevLeafType(parameters) in escapeDirectionTypes &&
        prevPrevLeafType(parameters) == NaturalTokenTypes.KW_ESCAPE
