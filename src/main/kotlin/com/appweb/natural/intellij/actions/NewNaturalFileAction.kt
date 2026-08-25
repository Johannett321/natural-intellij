package com.appweb.natural.intellij.actions

import com.appweb.natural.intellij.NaturalIcons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.psi.PsiDirectory
import org.jetbrains.annotations.NonNls

class NewNaturalFileAction : CreateFileFromTemplateAction (
    "Natural Object",
    "Creates a new Natural Object",
    NaturalIcons.PROGRAM
) {
    override fun buildDialog(
        project: Project,
        directory: PsiDirectory,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        builder.setTitle("New Natural Object")
        builder.addKind("Program", NaturalIcons.PROGRAM, "NaturalProgram")
        builder.addKind("Subprogram", NaturalIcons.SUBPROGRAM, "NaturalSUBProgram")
        builder.addKind("Copycode", NaturalIcons.COPYCODE, "NaturalCopycode")
        builder.addKind("Global data area", NaturalIcons.GDA, "NaturalGlobal")
        builder.addKind("Local data area", NaturalIcons.LDA, "NaturalLocal")
        builder.addKind("Parameter data area", NaturalIcons.PDA, "NaturalParameter")
        builder.addKind("Map", NaturalIcons.MAP, "NaturalMap")
        builder.setValidator(object : InputValidatorEx {
            override fun checkInput(inputString: String?) = (inputString?.length ?: 0) <= 8
            override fun canClose(inputString: String?) = (inputString?.length ?: 0) <= 8
            override fun getErrorText(inputString: @NonNls String?) = if ((inputString?.length ?: 0) > 8) "Name must be 8 characters or fewer" else null
        })
    }

    override fun getActionName(dir: PsiDirectory?, name: @NonNls String, template: @NonNls String?) = "Create Natural File '$name'"
}