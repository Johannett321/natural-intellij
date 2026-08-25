package com.appweb.natural.intellij.debug

import com.appweb.natural.intellij.language.filetypes.NaturalProgramFileType
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFileFactory
import com.intellij.xdebugger.XExpression
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.evaluation.EvaluationMode
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider

class NaturalDebuggerEditorsProvider : XDebuggerEditorsProvider() {

    override fun getFileType(): FileType = NaturalProgramFileType

    override fun createDocument(
        project: Project,
        expression: XExpression,
        sourcePosition: XSourcePosition?,
        mode: EvaluationMode,
    ): Document {
        val file = PsiFileFactory.getInstance(project).createFileFromText(
            "fragment.NSP",
            NaturalProgramFileType,
            expression.expression,
            System.currentTimeMillis(),
            true,
        )
        return PsiDocumentManager.getInstance(project).getDocument(file)!!
    }
}
