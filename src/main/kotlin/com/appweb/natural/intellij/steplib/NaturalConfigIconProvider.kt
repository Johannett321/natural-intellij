package com.appweb.natural.intellij.steplib

import com.appweb.natural.intellij.NaturalIcons
import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

/** Shows the Natural config icon for the `.natural` project settings file. */
class NaturalConfigIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if (element is PsiFile && element.name == ".natural") {
            return NaturalIcons.CONFIG
        }
        return null
    }
}
