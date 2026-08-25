package com.appweb.natural.intellij.nickname

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.ui.SimpleTextAttributes

@Suppress("DEPRECATION")
class NaturalProjectViewDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val file = node.virtualFile ?: return
        if (!SetNicknameAction.isNaturalFile(file)) return
        val nickname = NaturalNicknameService.getNickname(file) ?: return
        val existing = data.presentableText ?: return
        data.clearText()
        data.addText(nickname, SimpleTextAttributes.REGULAR_ATTRIBUTES)
        data.addText(" ($existing)", SimpleTextAttributes.GRAYED_ATTRIBUTES)
    }
}
