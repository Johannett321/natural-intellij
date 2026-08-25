package com.appweb.natural.intellij

import com.appweb.natural.intellij.language.filetypes.*
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

/**
 * Bulk parser test: runs the Natural lexer and parser over a corpus of real Natural sources and
 * reports every parse error, grouped by error-message pattern. Useful for measuring grammar
 * coverage against a large body of code.
 *
 * No corpus ships with this project - Natural sources are customer-specific - so the test is
 * skipped unless you point it at one:
 *
 *     ./gradlew test --tests "com.appweb.natural.intellij.NaturalParserBulkTest" \
 *         -Dnatural.source.root=/path/to/library
 *
 * Multiple roots may be separated by the platform path separator (`:` on Unix, `;` on Windows).
 * Each root is walked recursively and every file with a recognised Natural extension is parsed.
 */
class NaturalParserBulkTest : BasePlatformTestCase() {

    /** Corpus roots supplied via `-Dnatural.source.root`; empty when the property is unset. */
    private val sourceRoots: List<String>
        get() = System.getProperty(SOURCE_ROOT_PROPERTY)
            ?.split(File.pathSeparator)
            ?.map(String::trim)
            ?.filter(String::isNotEmpty)
            .orEmpty()

    // All Natural source extensions mapped to appropriate file types.
    // NSA (Adapters) and NSH (Helproutines) use the same grammar as programs.
    private val extensionToFileType: Map<String, LanguageFileType> = mapOf(
        "NSP" to NaturalProgramFileType,
        "NSN" to NaturalSubProgramFileType,
        "NSC" to NaturalCopyCodeFileType,
//        "NSD" to NaturalDDMFileType,
//        "NSG" to NaturalGDAFileType,
        "NSL" to NaturalLDAFileType,
//        "NSM" to NaturalMapFileType,
        "NPD" to NaturalPDAFileType,
//        "NSS" to NaturalSubRoutineFileType,
//        "NST" to NaturalTextFileType,
//        "NSA" to NaturalSubProgramFileType, // Adapters
//        "NSH" to NaturalProgramFileType,    // Helproutines
//        "NS7" to NaturalProgramFileType,
//        "NS8" to NaturalProgramFileType,
    )

    fun testParseAllFiles() {
        if (sourceRoots.isEmpty()) {
            println(
                "Skipping bulk parser test: no corpus configured. " +
                    "Pass -D$SOURCE_ROOT_PROPERTY=/path/to/library to run it."
            )
            return
        }

        val roots = sourceRoots.map { File(it) }
        roots.forEach { assertTrue("Source root does not exist: $it", it.exists()) }

        data class ParseError(
            val root: File,
            val file: File,
            val line: Int,
            val col: Int,
            val description: String,
            val context: String,
        )

        val allErrors = mutableListOf<ParseError>()
        var totalFiles = 0
        var skippedFiles = 0

        roots.forEach { root ->
            root.walkTopDown()
                .filter { it.isFile && !it.name.startsWith(".") }
                .forEach { file ->
                    val ext = file.extension.uppercase()
                    val fileType = extensionToFileType[ext] ?: run {
                        skippedFiles++
                        return@forEach
                    }
                    totalFiles++

                    val content = file.readText(Charsets.UTF_8)
                    val psiFile = myFixture.configureByText(fileType, content)

                    PsiTreeUtil.collectElementsOfType(psiFile, PsiErrorElement::class.java).forEach { err ->
                        val offset = err.textOffset
                        val before = content.take(offset)
                        val line = before.count { it == '\n' } + 1
                        val col = offset - (before.lastIndexOf('\n') + 1) + 1
                        val lineStart = before.lastIndexOf('\n') + 1
                        val lineEnd = content.indexOf('\n', offset).takeIf { it >= 0 } ?: content.length
                        val context = content.substring(lineStart, lineEnd).trim()
                        allErrors += ParseError(root, file, line, col, err.errorDescription, context)
                    }
                }
        }

        val errorFiles = allErrors.map { it.file }.toSet().size
        println("\n=== Natural Parser Bulk Test ===")
        println("Total files parsed : $totalFiles")
        println("Files with errors  : $errorFiles")
        println("Total error nodes  : ${allErrors.size}")
        println("Skipped (unknown)  : $skippedFiles")

        if (allErrors.isEmpty()) {
            println("All files parsed without errors!")
            return
        }

        // Group by normalised description (strip the trailing "found 'X'" noise so
        // similar errors collapse into one bucket)
        val grouped = allErrors
            .groupBy { normalise(it.description) }
            .entries
            .sortedByDescending { it.value.size }

        println("\n--- Errors by type (most common first) ---")
        grouped.forEach { (msg, errs) ->
            println("\n[${errs.size}x] $msg")
            // Show raw description for first error
            println("    raw: ${errs.first().description.take(200)}")
            // Show up to 5 example locations
            errs.take(5).forEach { e ->
                val rel = "${e.root.name}/${e.file.relativeTo(e.root)}"
                println("    ${rel}:${e.line}:${e.col}  →  ${e.context.take(80)}")
            }
        }

        println("\n--- Errors by root ---")
        allErrors.groupBy { it.root }
            .entries.sortedByDescending { it.value.size }
            .forEach { (r, errs) ->
                val files = errs.map { it.file }.toSet().size
                println("  ${r.name}: $files file(s), ${errs.size} error(s)")
            }

        println("\n--- Files with the most errors ---")
        allErrors.groupBy { it.file }
            .entries.sortedByDescending { it.value.size }.take(10)
            .forEach { (f, errs) ->
                val e = errs.first()
                println("  ${e.root.name}/${f.relativeTo(e.root)}: ${errs.size} error(s)")
            }

        fail("$errorFiles/$totalFiles files have parse errors (${allErrors.size} total error nodes). See test output above.")
    }

    private companion object {
        const val SOURCE_ROOT_PROPERTY = "natural.source.root"
    }

    /** Strip the "found 'TOKEN'" suffix so similar errors collapse into one group. */
    private fun normalise(description: String): String =
        description
            .replace(Regex(", found '.*'$"), "")
            .replace(Regex(" got '.*'$"), "")
            .trim()
}
