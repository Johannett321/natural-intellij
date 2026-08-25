package com.appweb.natural.intellij.nickname

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ide.projectView.ProjectView

class SetNicknameAction : AnAction("Set Nickname...") {

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file != null && isNaturalFile(file)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val project = e.project ?: return

        val current = NaturalNicknameService.getNickname(file)
        val nickname = Messages.showInputDialog(
            project,
            "Enter a nickname for '${file.name}' (leave empty to remove):",
            "Set File Nickname",
            Messages.getQuestionIcon(),
            current,
            null
        ) ?: return  // user cancelled

        NaturalNicknameService.setNickname(file, nickname.takeIf { it.isNotBlank() })

        ProjectView.getInstance(project).refresh()
        FileEditorManager.getInstance(project).updateFilePresentation(file)
    }

    companion object {
        private val NATURAL_EXTENSIONS = setOf("NSP", "NSN", "NSC", "NSD", "NSG", "NSL", "NSM", "NPD", "NSS", "NST")

        fun isNaturalFile(file: VirtualFile): Boolean {
            return file.extension?.uppercase() in NATURAL_EXTENSIONS
        }
    }
}
