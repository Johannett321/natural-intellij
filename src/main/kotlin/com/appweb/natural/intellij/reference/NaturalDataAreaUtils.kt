package com.appweb.natural.intellij.reference

import com.appweb.natural.intellij.language.filetypes.NaturalCopyCodeFileType
import com.appweb.natural.intellij.language.filetypes.NaturalProgramFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubProgramFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubRoutineFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubprogramParameterFileType
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalDefineDataPhase
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.psi.NaturalVariableDecl
import com.appweb.natural.intellij.steplib.NaturalSteplibService
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil

object NaturalDataAreaUtils {

    /** Returns the IDENTIFIER PSI element from a `LOCAL USING <name>` block, or null if not a USING block. */
    fun getUsingIdentifier(block: NaturalDataAreaBlock): PsiElement? {
        var node = block.node.firstChildNode
        var foundUsing = false
        while (node != null) {
            when {
                node.elementType == NaturalTypes.KW_USING -> foundUsing = true
                foundUsing && (node.elementType == NaturalTypes.IDENTIFIER ||
                        node.elementType == NaturalTypes.USER_VARIABLE) -> return node.psi
            }
            node = node.treeNext
        }
        return null
    }

    /**
     * Returns the file extensions to search when resolving a data area reference,
     * based on the scope keyword (LOCAL → NSL, GLOBAL → NSG, PARAMETER → NPD).
     */
    fun extensionsForBlock(block: NaturalDataAreaBlock): List<String> {
        return when (block.node.firstChildNode?.elementType) {
            NaturalTypes.KW_LOCAL -> listOf("NSL", "nsl", "NSA", "nsa")
            NaturalTypes.KW_GLOBAL -> listOf("NSG", "nsg")
            NaturalTypes.KW_PARAMETER -> listOf("NSA", "nsa")
            else -> listOf("NSL", "NSG", "NSA", "nsl", "nsg", "nsa")
        }
    }

    /** Resolves a `LOCAL USING <name>` block to the referenced data area file, or null. */
    fun resolveDataAreaFile(block: NaturalDataAreaBlock): PsiFile? {
        val identifier = getUsingIdentifier(block) ?: return null
        return resolveByName(
            name = identifier.text,
            extensions = extensionsForBlock(block),
            project = block.project,
            contextFile = block.containingFile
        )
    }

    /**
     * Resolves a CALLNAT target [name] to the matching `.NSN` subprogram file,
     * following the same library-first, steplib-ordered search as data area resolution.
     */
    fun resolveSubprogram(name: String, project: Project, contextFile: PsiFile?): PsiFile? =
        resolveByName(name, listOf("NSN", "nsn"), project, contextFile)

    /**
     * Resolves an INCLUDE target [name] to the matching `.NSC` copycode file,
     * following the same library-first, steplib-ordered search as data area resolution.
     */
    fun resolveCopyCode(name: String, project: Project, contextFile: PsiFile?): PsiFile? =
        resolveByName(name, listOf("NSC", "nsc"), project, contextFile)

    /**
     * Resolves a data area [name] to a PsiFile by searching the current library first,
     * then each steplib in order (as configured in `.natural`).
     *
     * Falls back to a project-wide search if [contextFile] is not provided or the
     * file is not inside a library directory.
     */
    fun resolveByName(
        name: String,
        extensions: List<String>,
        project: Project,
        contextFile: PsiFile? = null
    ): PsiFile? {
        val psiManager = PsiManager.getInstance(project)

        // Use project.basePath as the authoritative project root so the library name is
        // always the first path component relative to the project root — even when the
        // source file lives inside a type subfolder (e.g. MYLIB/Programs/file.NSP).
        val basePath = project.basePath
        val filePath = contextFile?.virtualFile?.path
        if (basePath != null && filePath != null && filePath.startsWith(basePath)) {
            val projectRootVFile = LocalFileSystem.getInstance().findFileByPath(basePath)
            if (projectRootVFile != null) {
                val relative = filePath.removePrefix(basePath).trimStart('/')
                val libraryName = relative.substringBefore('/')

                // Only use directory-based resolution when the file is inside a library folder
                if (libraryName.isNotEmpty() && libraryName != relative) {
                    val currentLibDir = projectRootVFile.findChild(libraryName)
                        ?: projectRootVFile.children.find {
                            it.isDirectory && it.name.equals(libraryName, ignoreCase = true)
                        }
                    val steplibs = NaturalSteplibService.getSteplibs(libraryName, project)

                    val searchDirs = buildList {
                        currentLibDir?.let { add(it) }
                        for (steplibName in steplibs) {
                            val dir = projectRootVFile.findChild(steplibName)
                                ?: projectRootVFile.children.find {
                                    it.isDirectory && it.name.equals(steplibName, ignoreCase = true)
                                }
                            dir?.let { add(it) }
                        }
                    }

                    for (dir in searchDirs) {
                        findInDirectory(dir, name, extensions)
                            ?.let { psiManager.findFile(it) }
                            ?.let { return it }
                    }
                    // Not found in directory tree — fall through to project-wide search below
                }
            }
        }

        // Project-wide fallback: try all case variants of the name, since FilenameIndex is case-sensitive
        val scope = GlobalSearchScope.allScope(project)
        val nameVariants = listOf(name, name.uppercase(), name.lowercase()).distinct()
        for (ext in extensions) {
            for (variant in nameVariants) {
                com.intellij.psi.search.FilenameIndex.getVirtualFilesByName("$variant.$ext", scope)
                    .firstNotNullOfOrNull { psiManager.findFile(it) }
                    ?.let { return it }
            }
        }
        return null
    }

    /**
     * Looks for `<name>.<ext>` inside [dir], trying name as-is and in uppercase/lowercase.
     * Also searches one level of subdirectories (e.g. "Local Data Areas/", "Programs/"),
     * since Natural libraries often organise files into type-named subfolders.
     */
    private fun findInDirectory(dir: VirtualFile, name: String, extensions: List<String>): VirtualFile? {
        val nameVariants = listOf(name, name.uppercase(), name.lowercase()).distinct()
        // Search directly in this directory
        for (ext in extensions) {
            for (variant in nameVariants) {
                dir.findChild("$variant.$ext")?.let { return it }
            }
        }
        // Search one level of subdirectories
        for (subDir in dir.children) {
            if (!subDir.isDirectory) continue
            for (ext in extensions) {
                for (variant in nameVariants) {
                    subDir.findChild("$variant.$ext")?.let { return it }
                }
            }
        }
        return null
    }

    /**
     * Resolves a qualified field reference like `GROUP.FIELD` by finding the data area
     * imported by [sourceFile] that owns a group named [qualifier], then returning the
     * [fieldName] declaration inside that data area.
     *
     * This prevents `DELL.RESTGJELD` from resolving to an unrelated `RESTGJELD` in a
     * different data area that happens to share the same field name.
     */
    fun findQualifiedDecl(fieldName: String, qualifier: String, sourceFile: PsiFile): NaturalVariableDecl? {
        val defineData = PsiTreeUtil.findChildOfType(sourceFile, NaturalDefineDataPhase::class.java)
        val importedFiles = defineData
            ?.let { PsiTreeUtil.findChildrenOfType(it, NaturalDataAreaBlock::class.java) }
            .orEmpty()
            .mapNotNull { resolveDataAreaFile(it) }

        val candidateFiles = buildList {
            add(sourceFile)
            addAll(importedFiles)
        }

        // Find the data area file that contains the qualifier group name
        val owningFile = candidateFiles.firstOrNull { f ->
            PsiTreeUtil.findChildrenOfType(f, NaturalVariableDecl::class.java)
                .any { it.name?.equals(qualifier, ignoreCase = true) == true }
        } ?: return null

        // Within that file look up the field
        return PsiTreeUtil.findChildrenOfType(owningFile, NaturalVariableDecl::class.java)
            .firstOrNull { it.name?.equals(fieldName, ignoreCase = true) == true }
    }

    /**
     * Returns all variable declarations visible in [file] through `LOCAL/GLOBAL/PARAMETER USING` imports.
     */
    fun getImportedVariableDecls(file: PsiFile): List<NaturalVariableDecl> {
        val defineData = PsiTreeUtil.findChildOfType(file, NaturalDefineDataPhase::class.java)
            ?: return emptyList()
        return PsiTreeUtil.findChildrenOfType(defineData, NaturalDataAreaBlock::class.java)
            .mapNotNull { resolveDataAreaFile(it) }
            .flatMap { PsiTreeUtil.findChildrenOfType(it, NaturalVariableDecl::class.java) }
    }

    /**
     * Returns all Natural program files in the project that import [dataAreaFile]
     * via a `LOCAL/GLOBAL/PARAMETER USING <name>` declaration.
     */
    fun findFilesImporting(dataAreaFile: PsiFile, project: Project): List<PsiFile> {
        val baseName = dataAreaFile.virtualFile?.nameWithoutExtension ?: return emptyList()
        val scope = GlobalSearchScope.allScope(project)
        val psiManager = PsiManager.getInstance(project)

        val naturalFileTypes = listOf(
            NaturalProgramFileType,
            NaturalSubProgramFileType,
            NaturalCopyCodeFileType,
            NaturalSubRoutineFileType,
            NaturalSubprogramParameterFileType
        )

        return naturalFileTypes
            .flatMap { fileType -> FileTypeIndex.getFiles(fileType, scope) }
            .mapNotNull { psiManager.findFile(it) }
            .filter { candidateFile ->
                val defineData = PsiTreeUtil.findChildOfType(candidateFile, NaturalDefineDataPhase::class.java)
                    ?: return@filter false
                PsiTreeUtil.findChildrenOfType(defineData, NaturalDataAreaBlock::class.java)
                    .any { block ->
                        getUsingIdentifier(block)?.text?.equals(baseName, ignoreCase = true) == true
                    }
            }
    }
}
