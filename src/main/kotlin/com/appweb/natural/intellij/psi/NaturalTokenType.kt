package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.language.NaturalLanguage
import com.intellij.psi.tree.IElementType

class NaturalTokenType(debugName: String) : IElementType(debugName, NaturalLanguage.INSTANCE) {
}