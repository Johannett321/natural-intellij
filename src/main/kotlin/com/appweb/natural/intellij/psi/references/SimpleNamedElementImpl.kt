package com.appweb.natural.intellij.psi.references

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class SimpleNamedElementImpl(
    node: ASTNode
) : ASTWrapperPsiElement(node), SimpleNamedElement