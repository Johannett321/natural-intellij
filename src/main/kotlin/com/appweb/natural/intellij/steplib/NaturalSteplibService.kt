package com.appweb.natural.intellij.steplib

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Reads the `.natural` file at the project root and exposes steplib ordering.
 *
 * In the file the relevant section looks like:
 * ```xml
 * <LibrarySteplib>
 *   <LibrarySteplibName>MYLIB</LibrarySteplibName>
 *   <LibrarySteplibExtensions>6;COMMON[-1,-1];SHARED[-1,-1];...</LibrarySteplibExtensions>
 * </LibrarySteplib>
 * ```
 *
 * Results are cached per project, keyed by the `.natural` file's modification stamp,
 * so they stay valid as long as the file is unchanged.
 */
object NaturalSteplibService {

    private data class CachedConfig(
        val modStamp: Long,
        val steplibs: Map<String, List<String>>  // uppercase library name → ordered steplib names
    )

    private val cache = mutableMapOf<String, CachedConfig>()

    /**
     * Returns the ordered list of steplib library names for [libraryName],
     * or empty if not configured.
     */
    fun getSteplibs(libraryName: String, project: Project): List<String> {
        return getConfig(project)[libraryName.uppercase()] ?: emptyList()
    }

    private fun getConfig(project: Project): Map<String, List<String>> {
        val basePath = project.basePath ?: return emptyMap()
        val naturalVFile = LocalFileSystem.getInstance().findFileByPath("$basePath/.natural")
            ?: return emptyMap()

        val cached = cache[basePath]
        if (cached != null && cached.modStamp == naturalVFile.modificationStamp) {
            return cached.steplibs
        }

        val parsed = parse(naturalVFile)
        cache[basePath] = CachedConfig(naturalVFile.modificationStamp, parsed)
        return parsed
    }

    private fun parse(file: VirtualFile): Map<String, List<String>> {
        return try {
            val doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(file.inputStream)
            doc.documentElement.normalize()

            val result = mutableMapOf<String, List<String>>()
            val entries = doc.getElementsByTagName("LibrarySteplib")
            for (i in 0 until entries.length) {
                val entry = entries.item(i) as? Element ?: continue
                val name = entry.getElementsByTagName("LibrarySteplibName")
                    .item(0)?.textContent?.trim()?.uppercase() ?: continue
                val extensionsRaw = entry.getElementsByTagName("LibrarySteplibExtensions")
                    .item(0)?.textContent ?: continue
                result[name] = parseExtensions(extensionsRaw)
            }
            result
        } catch (_: Exception) {
            emptyMap()
        }
    }

    /**
     * Parses `"6;COMMON[-1,-1];SHARED[-1,-1];..."` into `["COMMON", "SHARED", ...]`.
     * The first token is a count and is skipped.
     */
    private fun parseExtensions(raw: String): List<String> =
        raw.split(";")
            .drop(1)  // skip the leading count
            .mapNotNull { part ->
                val bracketIdx = part.indexOf('[')
                val name = (if (bracketIdx >= 0) part.substring(0, bracketIdx) else part).trim()
                name.uppercase().takeIf { it.isNotEmpty() }
            }
}
