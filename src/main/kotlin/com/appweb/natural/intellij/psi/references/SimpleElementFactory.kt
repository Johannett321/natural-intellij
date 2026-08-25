package com.appweb.natural.intellij.psi.references

import com.appweb.natural.intellij.language.NaturalLanguage
import com.appweb.natural.intellij.psi.NaturalFile
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory

object SimpleElementFactory {
    fun createProperty(project: Project, name: String?): NaturalVariableRef? {
        if (name == null) return null
        val file: NaturalFile = createFile(project, name)
        return file.getFirstChild() as NaturalVariableRef?
    }

    fun createFile(project: Project, text: String): NaturalFile {
        val name = "dummy.simple"
        return PsiFileFactory.getInstance(project).createFileFromText(name, NaturalLanguage.INSTANCE, text) as NaturalFile
    }
}