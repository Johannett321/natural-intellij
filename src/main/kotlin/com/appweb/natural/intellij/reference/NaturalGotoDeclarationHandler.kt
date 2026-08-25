package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.psi.NaturalCallnatStatement
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalIncludeStatement
import com.appweb.natural.intellij.psi.NaturalPsiImplUtil
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.lang.ASTNode
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.util.PsiTreeUtil

class NaturalGotoDeclarationHandler : GotoDeclarationHandler {

    override fun getGotoDeclarationTargets(
        sourceElement: PsiElement?,
        offset: Int,
        editor: Editor?
    ): Array<PsiElement>? {
        sourceElement ?: return null
        val tokenType = sourceElement.node?.elementType
        val file = sourceElement.containingFile ?: return null

        return when (val parent = sourceElement.parent) {

            // Ctrl+Click on the name in `LOCAL/GLOBAL/PARAMETER USING <name>`
            is NaturalDataAreaBlock -> {
                if (tokenType != NaturalTypes.USER_VARIABLE && tokenType != NaturalTypes.IDENTIFIER) return null
                val usingId = NaturalDataAreaUtils.getUsingIdentifier(parent)
                if (usingId == sourceElement) {
                    NaturalDataAreaUtils.resolveDataAreaFile(parent)?.let { arrayOf(it) }
                } else null
            }

            // Ctrl+Click on the subprogram name in `CALLNAT 'NAME'` or `CALLNAT NAME`
            is NaturalCallnatStatement -> {
                val isStringLit = tokenType == NaturalTypes.STRING_LITERAL
                val isIdentifier = tokenType == NaturalTypes.IDENTIFIER || tokenType == NaturalTypes.USER_VARIABLE
                if (!isStringLit && !isIdentifier) return null
                if (!isCallnatTarget(sourceElement.node)) return null

                val name = callnatName(sourceElement)
                NaturalDataAreaUtils.resolveSubprogram(name, file.project, file)
                    ?.let { arrayOf(it) }
            }

            // Ctrl+Click on the copycode name in `INCLUDE <name>`
            is NaturalIncludeStatement -> {
                if (tokenType != NaturalTypes.IDENTIFIER && tokenType != NaturalTypes.USER_VARIABLE) return null
                if (!isIncludeTarget(sourceElement.node)) return null
                NaturalDataAreaUtils.resolveCopyCode(sourceElement.text, file.project, file)
                    ?.let { arrayOf(it) }
            }

            // Ctrl+Click on a variable usage → navigate to declaration (current file, then imports)
            is NaturalVariableRef -> {
                if (tokenType != NaturalTypes.USER_VARIABLE && tokenType != NaturalTypes.IDENTIFIER) return null
                val name = sourceElement.text
                val qualifier = qualifierOf(sourceElement.node)
                val decl = if (qualifier != null) {
                    NaturalDataAreaUtils.findQualifiedDecl(name, qualifier, file)
                } else {
                    PsiTreeUtil.findChildrenOfType(file, NaturalVariableDecl::class.java)
                        .firstOrNull { it.name.equals(name, ignoreCase = true) }
                        ?: NaturalDataAreaUtils.getImportedVariableDecls(file)
                            .firstOrNull { it.name?.equals(name, ignoreCase = true) == true }
                }
                decl?.let { arrayOf(it) }
            }

            // Ctrl+Click on a variable declaration → find usages (current file + importing files)
            is NaturalVariableDecl -> {
                if (tokenType != NaturalTypes.USER_VARIABLE && tokenType != NaturalTypes.IDENTIFIER) return null
                val name = sourceElement.text
                val currentFileRefs = PsiTreeUtil.findChildrenOfType(file, NaturalVariableRef::class.java)
                    .filter { NaturalPsiImplUtil.nameNode(it.node)?.text.equals(name, ignoreCase = true) }

                val crossFileRefs = NaturalDataAreaUtils.findFilesImporting(file, file.project)
                    .flatMap { importingFile ->
                        PsiTreeUtil.findChildrenOfType(importingFile, NaturalVariableRef::class.java)
                            .filter { NaturalPsiImplUtil.nameNode(it.node)?.text.equals(name, ignoreCase = true) }
                    }

                (currentFileRefs + crossFileRefs)
                    .toTypedArray<PsiElement>()
                    .takeIf { it.isNotEmpty() }
            }

            else -> null
        }
    }

    /** True when [node] is the subprogram-name token immediately following KW_CALLNAT. */
    private fun isCallnatTarget(node: ASTNode): Boolean {
        var prev = node.treePrev
        while (prev != null && prev.elementType == TokenType.WHITE_SPACE) prev = prev.treePrev
        return prev?.elementType == NaturalTypes.KW_CALLNAT
    }

    /** True when [node] is the copycode-name token immediately following KW_INCLUDE. */
    private fun isIncludeTarget(node: ASTNode): Boolean {
        var prev = node.treePrev
        while (prev != null && prev.elementType == TokenType.WHITE_SPACE) prev = prev.treePrev
        return prev?.elementType == NaturalTypes.KW_INCLUDE
    }

    /** Extracts the bare subprogram name from a CALLNAT token (strips surrounding quotes). */
    private fun callnatName(element: PsiElement): String =
        element.text.trim().removeSurrounding("'").removeSurrounding("\"")

    /**
     * Returns the qualifier name if [node] is the field part of a `GROUP.FIELD` reference,
     * or null for a plain unqualified identifier.
     */
    private fun qualifierOf(node: ASTNode): String? {
        val prevDot = node.prevMeaningfulSibling() ?: return null
        if (prevDot.elementType != NaturalTypes.DOT) return null
        val qualifier = prevDot.prevMeaningfulSibling() ?: return null
        return if (qualifier.elementType == NaturalTypes.IDENTIFIER ||
            qualifier.elementType == NaturalTypes.USER_VARIABLE
        ) qualifier.text else null
    }

    private fun ASTNode.prevMeaningfulSibling(): ASTNode? {
        var node = treePrev
        while (node != null && node.elementType == TokenType.WHITE_SPACE) node = node.treePrev
        return node
    }
}
