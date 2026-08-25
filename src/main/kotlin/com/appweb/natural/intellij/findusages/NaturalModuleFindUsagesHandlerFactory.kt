package com.appweb.natural.intellij.findusages

import com.appweb.natural.intellij.index.NaturalCallnatIndex
import com.appweb.natural.intellij.index.NaturalDataAreaUsageIndex
import com.appweb.natural.intellij.language.filetypes.NaturalCopyCodeFileType
import com.appweb.natural.intellij.language.filetypes.NaturalGDAFileType
import com.appweb.natural.intellij.language.filetypes.NaturalLDAFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubProgramFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubprogramParameterFileType
import com.appweb.natural.intellij.psi.NaturalCallnatStatement
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalIncludeStatement
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesHandlerFactory
import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.TokenType
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.usageView.UsageInfo
import com.intellij.util.Processor
import com.intellij.util.indexing.FileBasedIndex

/**
 * Enables Alt+F7 ("Find Usages") on Natural module files:
 * - NSN subprograms  → finds all CALLNAT references
 * - NSC copycodes    → finds all INCLUDE references
 * - NSL LDA files    → finds all LOCAL USING references
 * - NSG GDA files    → finds all GLOBAL USING references
 * - NSA PDA files    → finds all PARAMETER USING and LOCAL USING references
 *
 * Results are powered by [NaturalCallnatIndex] and [NaturalDataAreaUsageIndex], which are
 * built incrementally by IntelliJ's file indexing infrastructure.
 */
class NaturalModuleFindUsagesHandlerFactory : FindUsagesHandlerFactory() {

    override fun canFindUsages(element: PsiElement): Boolean {
        val file = element as? PsiFile ?: return false
        return file.fileType is NaturalSubProgramFileType ||
               file.fileType is NaturalCopyCodeFileType ||
               file.fileType is NaturalSubprogramParameterFileType ||
               file.fileType is NaturalLDAFileType ||
               file.fileType is NaturalGDAFileType
    }

    override fun createFindUsagesHandler(element: PsiElement, forHighlightUsages: Boolean): FindUsagesHandler =
        NaturalModuleFindUsagesHandler(element as PsiFile)
}

private class NaturalModuleFindUsagesHandler(file: PsiFile) : FindUsagesHandler(file) {

    override fun processElementUsages(
        element: PsiElement,
        processor: Processor<in UsageInfo>,
        options: FindUsagesOptions
    ): Boolean {
        val psiFile = element as? PsiFile ?: return true
        val moduleName = psiFile.virtualFile?.nameWithoutExtension?.uppercase() ?: return true
        val project = psiFile.project
        val scope = toGlobalScope(options, project)

        return when (psiFile.fileType) {
            is NaturalSubProgramFileType ->
                processCallnatUsages(moduleName, NaturalCallnatIndex.CALLNAT, project, scope, processor)
            is NaturalCopyCodeFileType ->
                processCallnatUsages(moduleName, NaturalCallnatIndex.INCLUDE, project, scope, processor)
            is NaturalSubprogramParameterFileType ->
                processDataAreaUsages(moduleName, setOf(NaturalDataAreaUsageIndex.PARAMETER, NaturalDataAreaUsageIndex.LOCAL), project, scope, processor)
            is NaturalLDAFileType ->
                processDataAreaUsages(moduleName, setOf(NaturalDataAreaUsageIndex.LOCAL), project, scope, processor)
            is NaturalGDAFileType ->
                processDataAreaUsages(moduleName, setOf(NaturalDataAreaUsageIndex.GLOBAL), project, scope, processor)
            else -> true
        }
    }

    private fun processCallnatUsages(
        moduleName: String,
        expectedType: String,
        project: Project,
        scope: GlobalSearchScope,
        processor: Processor<in UsageInfo>
    ): Boolean {
        val psiManager = PsiManager.getInstance(project)
        return ReadAction.compute<Boolean, Throwable> {
            FileBasedIndex.getInstance().processValues(
                NaturalCallnatIndex.NAME,
                moduleName,
                null,
                { vFile, value ->
                    if (expectedType !in value) return@processValues true
                    val psiFile = psiManager.findFile(vFile) ?: return@processValues true
                    val stmtClass = if (expectedType == NaturalCallnatIndex.CALLNAT)
                        NaturalCallnatStatement::class.java
                    else
                        NaturalIncludeStatement::class.java
                    val keyword = if (expectedType == NaturalCallnatIndex.CALLNAT)
                        NaturalTypes.KW_CALLNAT
                    else
                        NaturalTypes.KW_INCLUDE
                    PsiTreeUtil.findChildrenOfType(psiFile, stmtClass).all { stmt ->
                        val nameEl = getNameAfterKeyword(stmt, keyword) ?: return@all true
                        val text = nameEl.text.trim().removeSurrounding("'").removeSurrounding("\"")
                        if (!text.equals(moduleName, ignoreCase = true)) return@all true
                        processor.process(UsageInfo(nameEl))
                    }
                },
                scope
            )
        }
    }

    private fun processDataAreaUsages(
        moduleName: String,
        expectedTypes: Set<String>,
        project: Project,
        scope: GlobalSearchScope,
        processor: Processor<in UsageInfo>
    ): Boolean {
        val psiManager = PsiManager.getInstance(project)
        return ReadAction.compute<Boolean, Throwable> {
            FileBasedIndex.getInstance().processValues(
                NaturalDataAreaUsageIndex.NAME,
                moduleName,
                null,
                { vFile, value ->
                    if (expectedTypes.none { it in value }) return@processValues true
                    val psiFile = psiManager.findFile(vFile) ?: return@processValues true
                    PsiTreeUtil.findChildrenOfType(psiFile, NaturalDataAreaBlock::class.java).all { block ->
                        val scopeType = blockScopeType(block) ?: return@all true
                        if (scopeType !in expectedTypes) return@all true
                        val nameEl = NaturalDataAreaUtils.getUsingIdentifier(block) ?: return@all true
                        if (!nameEl.text.equals(moduleName, ignoreCase = true)) return@all true
                        processor.process(UsageInfo(nameEl))
                    }
                },
                scope
            )
        }
    }

    private fun getNameAfterKeyword(stmt: PsiElement, keyword: com.intellij.psi.tree.IElementType): PsiElement? {
        var node = stmt.node.firstChildNode
        while (node != null) {
            if (node.elementType == keyword) {
                var next = node.treeNext
                while (next != null && next.elementType == TokenType.WHITE_SPACE) next = next.treeNext
                return next?.psi
            }
            node = node.treeNext
        }
        return null
    }

    private fun blockScopeType(block: NaturalDataAreaBlock): String? =
        when (block.node.firstChildNode?.elementType) {
            NaturalTypes.KW_LOCAL -> NaturalDataAreaUsageIndex.LOCAL
            NaturalTypes.KW_GLOBAL -> NaturalDataAreaUsageIndex.GLOBAL
            NaturalTypes.KW_PARAMETER -> NaturalDataAreaUsageIndex.PARAMETER
            else -> null
        }

    private fun toGlobalScope(options: FindUsagesOptions, project: Project): GlobalSearchScope =
        options.searchScope as? GlobalSearchScope ?: GlobalSearchScope.allScope(project)
}
