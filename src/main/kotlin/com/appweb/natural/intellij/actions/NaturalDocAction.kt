package com.appweb.natural.intellij.actions

import com.appweb.natural.intellij.documentation.NaturalDocUtils
import com.appweb.natural.intellij.language.NaturalFileType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiManager
import java.io.File

/**
 * Right-click action that creates (or opens) a Markdown documentation file for the
 * selected Natural source file.
 *
 * Doc files live under `<project-root>/.IntelliJNatural/docs/` mirroring the
 * library/folder structure of the source file.
 */
class NaturalDocAction : AnAction("Go to NaturalDoc") {

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file != null && file.fileType is NaturalFileType
    }

    override fun actionPerformed(e: AnActionEvent) {
        val vFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val project = e.project ?: return

        val psiFile = PsiManager.getInstance(project).findFile(vFile) ?: return
        val docFile = NaturalDocUtils.docFileFor(psiFile) ?: return

        // Create the file (and any missing parent directories) if it doesn't exist yet
        if (!docFile.exists()) {
            docFile.parentFile.mkdirs()
            docFile.writeText("# ${vFile.nameWithoutExtension}\n\n")
        }

        // Refresh IntelliJ's VFS so the new file is visible, then open it in the editor
        val docVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(docFile) ?: return
        FileEditorManager.getInstance(project).openFile(docVFile, true)
    }
}
