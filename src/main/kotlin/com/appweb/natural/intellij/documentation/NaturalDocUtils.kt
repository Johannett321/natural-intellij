package com.appweb.natural.intellij.documentation

import com.intellij.psi.PsiFile
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import java.io.File

object NaturalDocUtils {

    private const val DOCS_DIR = ".IntelliJNatural/docs"

    private val options = MutableDataSet().apply {
        set(Parser.EXTENSIONS, listOf(TablesExtension.create()))
    }
    private val parser = Parser.builder(options).build()
    private val renderer = HtmlRenderer.builder(options).build()

    /** Returns the `.md` doc file for a given Natural source file, or null if not inside the project. */
    fun docFileFor(naturalFile: PsiFile): File? {
        val basePath = naturalFile.project.basePath ?: return null
        val filePath = naturalFile.virtualFile?.path ?: return null
        if (!filePath.startsWith(basePath)) return null
        val relative = filePath.removePrefix(basePath).trimStart('/')
        val docRelative = relative.substringBeforeLast('.') + ".md"
        return File(basePath, "$DOCS_DIR/$docRelative")
    }

    /** Returns the doc file path as a string (does not have to exist yet). */
    fun docPathFor(naturalFile: PsiFile): String? = docFileFor(naturalFile)?.path

    /**
     * Reads the doc file for [naturalFile] (if it exists) and renders it as HTML.
     * Returns a "no docs" message when the file is missing or empty.
     */
    fun renderDoc(naturalFile: PsiFile): String {
        val docFile = docFileFor(naturalFile)
        val markdown = docFile?.takeIf { it.exists() }?.readText()?.trim()
        return if (!markdown.isNullOrEmpty()) {
            markdownToHtml(markdown)
        } else {
            "<i>No docs added for this file.</i>" +
                    "<br><br>Right-click the file and choose <b>Go to NaturalDoc</b> to add documentation."
        }
    }

    /** Converts Markdown to an HTML fragment using flexmark (CommonMark + tables). */
    fun markdownToHtml(markdown: String): String =
        renderer.render(parser.parse(markdown))
}
