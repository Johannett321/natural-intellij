package com.appweb.natural.intellij.intentions

import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalDefineDataPhase
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.psi.NaturalVariableRef
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.ConstantNode
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class GenerateVariableIntentionAction : PsiElementBaseIntentionAction() {

    override fun getText() = "Add variable to DEFINE DATA"
    override fun getFamilyName() = "Natural"

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        val ref = findVariableRef(element, editor) ?: return false
        val file = ref.containingFile ?: return false
        return isUnresolved(ref, file)
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val ref = findVariableRef(element, editor) ?: return
        val file = ref.containingFile ?: return
        // Strip subscript suffix like (1) or (*) before parsing dotted name
        val cleanText = ref.text.trim().substringBefore('(').trim()
        val dotIdx = cleanText.indexOf('.')
        if (dotIdx > 0) {
            val qualifier = cleanText.substring(0, dotIdx)
            val fieldName = cleanText.substring(dotIdx + 1)
            handleQualifiedVariable(project, editor, file, qualifier, fieldName)
        } else {
            handleSimpleVariable(project, editor, file, cleanText)
        }
    }

    private fun isUnresolved(ref: NaturalVariableRef, file: PsiFile): Boolean {
        val cleanText = ref.text.trim().substringBefore('(').trim()
        val dotIdx = cleanText.indexOf('.')
        return if (dotIdx > 0) {
            // For GROUP.FIELD: offer only when the GROUP exists but FIELD is not defined under it.
            // ref.reference resolves the first token (GROUP), so non-null means the group exists.
            val qualifier = cleanText.substring(0, dotIdx)
            val fieldName = cleanText.substring(dotIdx + 1)
            ref.reference?.resolve() != null &&
                NaturalDataAreaUtils.findQualifiedDecl(fieldName, qualifier, file) == null
        } else {
            ref.reference?.resolve() == null
        }
    }

    // ── Simple variable (no dot) ──────────────────────────────────────────────

    private fun handleSimpleVariable(project: Project, editor: Editor, file: PsiFile, varName: String) {
        val defineData = PsiTreeUtil.findChildOfType(file, NaturalDefineDataPhase::class.java) ?: return
        // Find first inline LOCAL/GLOBAL/PARAMETER block (not a USING reference)
        val block = PsiTreeUtil.findChildrenOfType(defineData, NaturalDataAreaBlock::class.java)
            .firstOrNull { NaturalDataAreaUtils.getUsingIdentifier(it) == null } ?: return
        insertWithTemplate(project, editor, file.virtualFile, block.textRange.endOffset, 1, varName)
    }

    // ── Qualified variable (GROUP.FIELD) ─────────────────────────────────────

    private fun handleQualifiedVariable(
        project: Project, editor: Editor,
        file: PsiFile, qualifier: String, fieldName: String
    ) {
        val defineData = PsiTreeUtil.findChildOfType(file, NaturalDefineDataPhase::class.java) ?: return

        // Search USING blocks first — the group may live in an external data area file
        for (block in PsiTreeUtil.findChildrenOfType(defineData, NaturalDataAreaBlock::class.java)) {
            val resolved = NaturalDataAreaUtils.resolveDataAreaFile(block) ?: continue
            val group = PsiTreeUtil.findChildrenOfType(resolved, NaturalVariableDecl::class.java)
                .firstOrNull { it.name?.equals(qualifier, ignoreCase = true) == true } ?: continue
            insertUnderGroup(project, editor, group, resolved, fieldName)
            return
        }

        // Fall back to current file
        val group = PsiTreeUtil.findChildrenOfType(file, NaturalVariableDecl::class.java)
            .firstOrNull { it.name?.equals(qualifier, ignoreCase = true) == true } ?: return
        insertUnderGroup(project, editor, group, file, fieldName)
    }

    private fun insertUnderGroup(
        project: Project, editor: Editor,
        group: NaturalVariableDecl, targetFile: PsiFile, fieldName: String
    ) {
        val allDecls = PsiTreeUtil.findChildrenOfType(targetFile, NaturalVariableDecl::class.java).toList()
        val groupIdx = allDecls.indexOf(group)
        val groupLevel = getLevelNumber(group)

        // Walk forward past all child levels to find the last field belonging to this group
        var lastChild: NaturalVariableDecl? = null
        for (i in (groupIdx + 1) until allDecls.size) {
            if (getLevelNumber(allDecls[i]) <= groupLevel) break
            lastChild = allDecls[i]
        }

        val insertOffset = (lastChild ?: group).textRange.endOffset
        insertWithTemplate(project, editor, targetFile.virtualFile, insertOffset, groupLevel + 1, fieldName)
    }

    // ── Template insertion ────────────────────────────────────────────────────

    private fun insertWithTemplate(
        project: Project, editor: Editor,
        vFile: VirtualFile?, insertOffset: Int, level: Int, varName: String
    ) {
        vFile ?: return
        // Navigate to the target file at the insertion point (opens external files automatically)
        OpenFileDescriptor(project, vFile, insertOffset).navigate(true)
        val targetEditor = FileEditorManager.getInstance(project).selectedTextEditor ?: editor

        val indent = "  ".repeat(level - 1)
        val templateText = "\n$indent$level $varName (\$TYPE\$)\$END\$"
        val manager = TemplateManager.getInstance(project)
        val template = manager.createTemplate("", "", templateText) as TemplateImpl
        template.isToReformat = false
        template.addVariable("TYPE", ConstantNode("A"), ConstantNode("A"), true)
        manager.startTemplate(targetEditor, template)
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun getLevelNumber(decl: NaturalVariableDecl): Int =
        decl.node.firstChildNode?.text?.trim()?.toIntOrNull() ?: 1

    private fun findVariableRef(element: PsiElement, editor: Editor): NaturalVariableRef? {
        PsiTreeUtil.getParentOfType(element, NaturalVariableRef::class.java, false)?.let { return it }
        val offset = editor.caretModel.offset
        if (offset > 0) {
            val prev = element.containingFile?.findElementAt(offset - 1)
            if (prev != null && prev !== element) {
                PsiTreeUtil.getParentOfType(prev, NaturalVariableRef::class.java, false)?.let { return it }
            }
        }
        return null
    }
}
