package com.appweb.natural.intellij.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.ConstantNode
import com.intellij.codeInsight.template.impl.TemplateImpl

/**
 * Insert handler that expands a lookup item into a multi-line snippet with
 * tab-stop placeholders. After insertion, the cursor lands on the first
 * placeholder and Tab advances through the remaining ones.
 *
 * @param templateText The template string. Use $VAR$ for tab stops and $END$
 *                     for the final cursor position.
 */
class NaturalTemplateInsertHandler(
    private val templateText: String,
    private val reformat: Boolean = true
) : InsertHandler<LookupElement> {
    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.editor
        val document = editor.document

        // Remove the text that was inserted by the completion item itself
        document.deleteString(context.startOffset, context.tailOffset)

        val manager = TemplateManager.getInstance(context.project)
        val template = manager.createTemplate("", "", templateText) as TemplateImpl
        template.isToReformat = reformat

        // Parse $VAR$ placeholders from the template text and register each as
        // an editable tab stop. $END$ is a built-in and must be skipped.
        val variableRegex = Regex("""\$(\w+)\$""")
        variableRegex.findAll(templateText)
            .map { it.groupValues[1] }
            .filter { it != "END" }
            .distinct()
            .forEach { name ->
                template.addVariable(name, ConstantNode(""), ConstantNode(""), true)
            }

        manager.startTemplate(editor, template)
    }
}
