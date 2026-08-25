package com.appweb.natural.intellij.psi

import com.appweb.natural.intellij.psi.references.SimpleElementFactory
import com.appweb.natural.intellij.reference.NaturalVariableReference
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.tree.Factory
import com.intellij.psi.impl.source.tree.SharedImplUtil

object NaturalPsiImplUtil {

    /** Returns the name token (USER_VARIABLE or IDENTIFIER) of a variable node. */
    fun nameNode(node: ASTNode): ASTNode? =
        node.findChildByType(NaturalTypes.USER_VARIABLE)
            ?: node.findChildByType(NaturalTypes.IDENTIFIER)

    /** Replaces [nameNode] under [parentNode] with a new leaf containing [newName]. */
    fun replaceNameToken(parentNode: ASTNode, nameNode: ASTNode, newName: String, manager: PsiManager) {
        val newLeaf = Factory.createSingleLeafElement(
            nameNode.elementType, newName, 0, newName.length,
            SharedImplUtil.findCharTableByTree(nameNode), manager
        )
        parentNode.replaceChild(nameNode, newLeaf)
    }

    // ── NaturalVariableRef ────────────────────────────────────────────────────

    fun getKey(element: NaturalVariableRef?): String? {
        val keyNode: ASTNode? = element?.node?.findChildByType(NaturalTypes.IDENTIFIER)
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.text.replace("\\\\ ".toRegex(), " ")
        } else {
            return null
        }
    }

    fun getValue(element: NaturalVariableRef): String? =
        element.node.findChildByType(NaturalTypes.IDENTIFIER)?.text

    fun getName(element: NaturalVariableRef?): String? = getKey(element)

    fun getReference(element: NaturalVariableRef): PsiReference? {
        val nameNode = nameNode(element.node) ?: return null
        val start = nameNode.startOffsetInParent
        return NaturalVariableReference(element, TextRange(start, start + nameNode.textLength))
    }

    fun setName(element: NaturalVariableRef, newName: String?): PsiElement {
        val keyNode: ASTNode? = element.node.findChildByType(NaturalTypes.IDENTIFIER)
        if (keyNode != null) {
            val property: NaturalVariableRef? =
                SimpleElementFactory.createProperty(element.project, newName)
            val newKeyNode: ASTNode? = property?.firstChild?.node
            if (newKeyNode != null) {
                element.node.replaceChild(keyNode, newKeyNode)
            }
        }
        return element
    }

    fun getNameIdentifier(element: NaturalVariableRef): PsiElement? =
        element.node.findChildByType(NaturalTypes.IDENTIFIER)?.psi

    // ── NaturalVariableDecl ───────────────────────────────────────────────────

    fun getName(element: NaturalVariableDecl): String? = nameNode(element.node)?.text

    fun setName(element: NaturalVariableDecl, newName: String): PsiElement {
        val nameNode = nameNode(element.node) ?: return element
        replaceNameToken(element.node, nameNode, newName, element.manager)
        return element
    }

    fun getNameIdentifier(element: NaturalVariableDecl): PsiElement? = nameNode(element.node)?.psi
}
