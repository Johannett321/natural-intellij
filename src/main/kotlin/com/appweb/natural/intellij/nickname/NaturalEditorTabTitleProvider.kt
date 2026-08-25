package com.appweb.natural.intellij.nickname

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class NaturalEditorTabTitleProvider : EditorTabTitleProvider {
    override fun getEditorTabTitle(project: Project, file: VirtualFile): String? {
        if (!SetNicknameAction.isNaturalFile(file)) return null
        val nickname = NaturalNicknameService.getNickname(file) ?: return null
        return "$nickname (${file.name})"
    }
}
