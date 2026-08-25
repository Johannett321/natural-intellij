package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.language.NaturalLanguage
import com.appweb.natural.intellij.language.filetypes.NaturalProgramFileType
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class NaturalFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NaturalLanguage.INSTANCE) {

    override fun getFileType(): FileType =
        viewProvider.fileType.let { if (it is FileType) it else NaturalProgramFileType }

}