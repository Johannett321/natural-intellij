package com.appweb.natural.intellij.language

import com.intellij.lang.Language

class NaturalLanguage private constructor() : Language("Natural") {
    companion object {
        @JvmField
        val INSTANCE = NaturalLanguage()
    }
}