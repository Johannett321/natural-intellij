package com.appweb.natural.intellij.intentions

import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalDefineDataPhase
import com.appweb.natural.intellij.psi.NaturalPsiImplUtil
import com.appweb.natural.intellij.psi.NaturalStatement
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class ExtractSubprogramIntentionAction : PsiElementBaseIntentionAction() {

    private data class DeclGroup(val level1: NaturalVariableDecl, val all: List<NaturalVariableDecl>)
    private enum class Category { PARAMETER, LOCAL, UNRELATED }

    override fun getText() = "Extract subprogram"
    override fun getFamilyName() = "Natural"
    override fun startInWriteAction() = false

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        if (!editor.selectionModel.hasSelection()) return false
        val file = element.containingFile ?: return false
        return findSelectedStatements(editor, file).isNotEmpty()
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val file = element.containingFile ?: return
        val statements = findSelectedStatements(editor, file)
        if (statements.isEmpty()) return

        val rawName = Messages.showInputDialog(
            project, "Subprogram name (max 8 characters):", "Extract Subprogram", null
        )?.trim() ?: return
        val name = rawName.take(8).uppercase()
        if (name.isBlank()) return

        val document = editor.document
        val selStart = statements.first().textRange.startOffset
        val selEnd = statements.last().textRange.endOffset
        val selectedText = document.getText(TextRange(selStart, selEnd))

        // ── Variable analysis ────────────────────────────────────────────────
        val defineData = PsiTreeUtil.findChildOfType(file, NaturalDefineDataPhase::class.java)
        val defineDataEnd = defineData?.textRange?.endOffset ?: 0

        val inlineLocalBlock = defineData?.let { dd ->
            PsiTreeUtil.findChildrenOfType(dd, NaturalDataAreaBlock::class.java)
                .firstOrNull { block ->
                    block.node.firstChildNode?.elementType == NaturalTypes.KW_LOCAL &&
                    NaturalDataAreaUtils.getUsingIdentifier(block) == null
                }
        }

        val allDecls = inlineLocalBlock
            ?.let { PsiTreeUtil.findChildrenOfType(it, NaturalVariableDecl::class.java).toList() }
            ?: emptyList()

        // All variable refs in the code section only (not in DEFINE DATA)
        val codeRefs = PsiTreeUtil.findChildrenOfType(file, NaturalVariableRef::class.java)
            .filter { it.textRange.startOffset >= defineDataEnd }

        val groups = buildDeclGroups(allDecls)
        val parameters = mutableListOf<DeclGroup>()
        val locals = mutableListOf<DeclGroup>()

        for (group in groups) {
            val groupNames = group.all.mapNotNull { it.name }.toSet()
            val refsToGroup = codeRefs.filter { ref ->
                NaturalPsiImplUtil.getName(ref)?.let { n -> groupNames.any { n.equals(it, ignoreCase = true) } } == true
            }
            val inSel = refsToGroup.any { it.textRange.startOffset in selStart until selEnd }
            val outSel = refsToGroup.any { it.textRange.startOffset < selStart || it.textRange.startOffset >= selEnd }
            when {
                inSel && outSel -> parameters.add(group)
                inSel           -> locals.add(group)
            }
        }

        // ── Build subprogram file content ────────────────────────────────────
        val subprogramContent = buildSubprogramContent(parameters, locals, selectedText)

        // ── CALLNAT line ─────────────────────────────────────────────────────
        val paramNames = parameters.mapNotNull { it.level1.name }.filter { it.isNotBlank() }
        val callnat = buildString {
            append("CALLNAT '$name'")
            if (paramNames.isNotEmpty()) append(' ').append(paramNames.joinToString(" "))
        }

        val lineStart = document.getLineStartOffset(document.getLineNumber(selStart))
        val indent = document.getText(TextRange(lineStart, selStart)).takeWhile { it == ' ' || it == '\t' }

        val targetDir = file.virtualFile?.parent ?: return

        WriteCommandAction.runWriteCommandAction(project) {
            // Create or overwrite the subprogram file
            val vFile = targetDir.findChild("$name.NSN") ?: targetDir.createChildData(null, "$name.NSN")
            @Suppress("UnstableApiUsage")
            vFile.setBinaryContent(subprogramContent.toByteArray(Charsets.UTF_8))

            // 1. Replace selected code with CALLNAT (highest offset — doesn't affect DEFINE DATA below)
            document.replaceString(selStart, selEnd, "$indent$callnat")

            // 2. Remove LOCAL-only declarations from DEFINE DATA (bottom-to-top to preserve offsets)
            val declsToRemove = locals.flatMap { it.all }
                .sortedByDescending { it.textRange.startOffset }
            for (decl in declsToRemove) {
                val ln = document.getLineNumber(decl.textRange.startOffset)
                val lStart = document.getLineStartOffset(ln)
                val lEnd = document.getLineEndOffset(ln)
                val deleteEnd = if (lEnd < document.textLength) lEnd + 1 else lEnd
                document.deleteString(lStart, deleteEnd)
            }
        }
    }

    // ── Subprogram file builder ───────────────────────────────────────────────

    private fun buildSubprogramContent(
        parameters: List<DeclGroup>,
        locals: List<DeclGroup>,
        codeText: String
    ) = buildString {
        append("DEFINE DATA\n")
        if (parameters.isNotEmpty()) {
            append("PARAMETER\n")
            for (g in parameters) for (d in g.all) appendLine("${"  ".repeat(getLevelNumber(d) - 1)}${d.text}")
        }
        if (locals.isNotEmpty()) {
            append("LOCAL\n")
            for (g in locals) for (d in g.all) appendLine("${"  ".repeat(getLevelNumber(d) - 1)}${d.text}")
        }
        append("END-DEFINE\n\n")
        append(codeText.trimEnd())
        append("\n\nEND\n")
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Groups a flat list of variable declarations by level-1 root, attaching sub-level children. */
    private fun buildDeclGroups(decls: List<NaturalVariableDecl>): List<DeclGroup> {
        val groups = mutableListOf<DeclGroup>()
        var i = 0
        while (i < decls.size) {
            val decl = decls[i]
            if (getLevelNumber(decl) == 1) {
                val group = mutableListOf(decl)
                var j = i + 1
                while (j < decls.size && getLevelNumber(decls[j]) > 1) { group.add(decls[j]); j++ }
                groups.add(DeclGroup(decl, group))
                i = j
            } else {
                i++
            }
        }
        return groups
    }

    private fun getLevelNumber(decl: NaturalVariableDecl): Int =
        decl.node.firstChildNode?.text?.trim()?.toIntOrNull() ?: 1

    private fun findSelectedStatements(editor: Editor, file: PsiFile): List<NaturalStatement> {
        val selStart = editor.selectionModel.selectionStart
        val selEnd = editor.selectionModel.selectionEnd
        val all = PsiTreeUtil.findChildrenOfType(file, NaturalStatement::class.java)
            .filter { it.textRange.startOffset >= selStart && it.textRange.endOffset <= selEnd }
        if (all.isEmpty()) return emptyList()
        val commonParent = all.first().parent ?: return emptyList()
        return all.filter { it.parent == commonParent }
    }
}
