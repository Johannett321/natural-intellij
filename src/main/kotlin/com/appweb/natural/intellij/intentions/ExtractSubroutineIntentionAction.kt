package com.appweb.natural.intellij.intentions

import com.appweb.natural.intellij.psi.NaturalStatement
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class ExtractSubroutineIntentionAction : PsiElementBaseIntentionAction() {

    override fun getText() = "Extract subroutine"
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

        val name = Messages.showInputDialog(project, "Subroutine name:", "Extract Subroutine", null)
            ?.trim() ?: return
        if (name.isBlank()) return

        val document = editor.document
        val selStart = statements.first().textRange.startOffset
        val selEnd = statements.last().textRange.endOffset
        val selectedText = document.getText(TextRange(selStart, selEnd))

        val subroutineBody = normalizeIndent(selectedText, "  ")
        val sep = "***********************************************************************"
        val endOffset = findEndStatementOffset(document)

        val lineStart = document.getLineStartOffset(document.getLineNumber(selStart))
        val originalIndent = document.getText(TextRange(lineStart, selStart)).takeWhile { it == ' ' || it == '\t' }

        WriteCommandAction.runWriteCommandAction(project) {
            // Insert at higher offset first so selStart/selEnd are unaffected
            document.insertString(endOffset, "\n$sep\nDEFINE SUBROUTINE $name\n$sep\n$subroutineBody\nEND-SUBROUTINE\n")
            document.replaceString(selStart, selEnd, "${originalIndent}PERFORM $name")
        }
    }

    private fun findSelectedStatements(editor: Editor, file: PsiFile): List<NaturalStatement> {
        val selStart = editor.selectionModel.selectionStart
        val selEnd = editor.selectionModel.selectionEnd
        val all = PsiTreeUtil.findChildrenOfType(file, NaturalStatement::class.java)
            .filter { it.textRange.startOffset >= selStart && it.textRange.endOffset <= selEnd }
        if (all.isEmpty()) return emptyList()
        val commonParent = all.first().parent ?: return emptyList()
        return all.filter { it.parent == commonParent }
    }

    private fun normalizeIndent(text: String, indent: String): String {
        val lines = text.lines()
        val minIndent = lines.filter { it.isNotBlank() }.minOfOrNull { l -> l.length - l.trimStart().length } ?: 0
        return lines.joinToString("\n") { line -> if (line.isBlank()) line else indent + line.drop(minIndent) }
    }

    private fun findEndStatementOffset(document: Document): Int {
        for (line in document.lineCount - 1 downTo 0) {
            val start = document.getLineStartOffset(line)
            val end = document.getLineEndOffset(line)
            if (document.getText(TextRange(start, end)).trim().equals("END", ignoreCase = true))
                return start
        }
        return document.textLength
    }
}
