package com.appweb.natural.intellij.intentions

import com.appweb.natural.intellij.psi.NaturalSubroutineRef
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.ConstantNode
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class GenerateSubroutineIntentionAction : PsiElementBaseIntentionAction() {

    override fun getText() = "Generate subroutine"
    override fun getFamilyName() = "Natural"

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        val ref = findSubroutineRef(element, editor) ?: return false
        return ref.reference?.resolve() == null
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val ref = findSubroutineRef(element, editor) ?: return
        val subroutineName = ref.reference?.canonicalText ?: ref.text.trim()

        val document = editor.document
        editor.caretModel.moveToOffset(findEndStatementOffset(document))

        val sep = "************************************************************************"
        val templateText =
            "\n$sep\nDEFINE SUBROUTINE $subroutineName\n$sep\n  \$CODE\$\nEND-SUBROUTINE\n\n\$END\$"

        val manager = TemplateManager.getInstance(project)
        val template = manager.createTemplate("", "", templateText) as TemplateImpl
        template.isToReformat = false
        template.addVariable("CODE", ConstantNode(""), ConstantNode(""), true)

        manager.startTemplate(editor, template)
    }

    private fun findSubroutineRef(element: PsiElement, editor: Editor): NaturalSubroutineRef? {
        PsiTreeUtil.getParentOfType(element, NaturalSubroutineRef::class.java, false)
            ?.let { return it }
        val offset = editor.caretModel.offset
        if (offset > 0) {
            val prev = element.containingFile?.findElementAt(offset - 1)
            if (prev != null && prev !== element) {
                PsiTreeUtil.getParentOfType(prev, NaturalSubroutineRef::class.java, false)
                    ?.let { return it }
            }
        }
        return null
    }

    private fun findEndStatementOffset(document: Document): Int {
        for (line in document.lineCount - 1 downTo 0) {
            val lineStart = document.getLineStartOffset(line)
            val lineEnd = document.getLineEndOffset(line)
            val lineText = document.getText(TextRange(lineStart, lineEnd)).trim()
            if (lineText.equals("END", ignoreCase = true)) {
                return lineStart
            }
        }
        return document.textLength
    }
}
