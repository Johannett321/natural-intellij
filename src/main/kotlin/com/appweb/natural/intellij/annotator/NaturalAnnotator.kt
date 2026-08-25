package com.appweb.natural.intellij.annotator

import com.appweb.natural.intellij.intentions.GenerateSubroutineIntentionAction
import com.appweb.natural.intellij.psi.NaturalSubroutineRef
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class NaturalAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is NaturalSubroutineRef) return
        if (element.reference?.resolve() != null) return

        holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved subroutine '${element.text.trim()}'")
            .range(element)
            .withFix(GenerateSubroutineIntentionAction())
            .create()
    }
}
