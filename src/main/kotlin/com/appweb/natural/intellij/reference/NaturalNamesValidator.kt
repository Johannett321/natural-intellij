package com.appweb.natural.intellij.reference

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class NaturalNamesValidator : NamesValidator {
    override fun isKeyword(name: String, project: Project?) = false

    override fun isIdentifier(name: String, project: Project?): Boolean {
        if (name.isEmpty()) return false
        val first = name[0]
        if (!first.isLetter() && first != '#' && first != '+') return false
        return name.all { it.isLetterOrDigit() || it == '-' || it == '_' || it == '#' || it == '/' }
    }
}
