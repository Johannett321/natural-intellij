package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.language.NaturalLanguage
import com.intellij.psi.tree.IFileElementType

object NaturalElementTypes {

    @JvmField
    val FILE = IFileElementType(NaturalLanguage.INSTANCE)
}