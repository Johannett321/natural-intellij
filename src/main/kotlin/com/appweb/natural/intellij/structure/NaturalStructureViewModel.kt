package com.appweb.natural.intellij.structure

import com.appweb.natural.intellij.psi.NaturalDefineSubroutineBlock
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class NaturalStructureViewModel(psiFile: PsiFile, editor: Editor?) :
    StructureViewModelBase(psiFile, editor, NaturalStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement) = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement) =
        element.value is NaturalDefineSubroutineBlock || element.value is NaturalVariableDecl
}
