package com.appweb.natural.intellij.toolwindow

import com.appweb.natural.intellij.documentation.NaturalDocUtils
import com.appweb.natural.intellij.language.NaturalFileType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.psi.PsiManager
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.HTMLEditorKitBuilder
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JEditorPane
import javax.swing.JPanel
import javax.swing.JTextArea

class NaturalDocToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = NaturalDocPanel(project)
        Disposer.register(toolWindow.disposable, panel)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

class NaturalDocPanel(private val project: Project) : JPanel(BorderLayout()), FileEditorManagerListener, Disposable {

    private var currentFile: VirtualFile? = null
    private var isEditing = false

    private val scheme get() = EditorColorsManager.getInstance().globalScheme

    private val editorPane = JEditorPane().apply {
        isEditable = false
        border = JBUI.Borders.empty()
        editorKit = HTMLEditorKitBuilder.simple().also { kit ->
            kit.styleSheet.addRule("table { border-collapse: collapse; margin: 6px 0; }")
            kit.styleSheet.addRule("th, td { padding: 4px 8px; }")
            kit.styleSheet.addRule("th { font-weight: bold; }")
        }
    }

    private val editArea = JTextArea().apply {
        lineWrap = true
        wrapStyleWord = true
        border = JBUI.Borders.empty(8)
    }

    private val cards = JPanel(CardLayout())
    private val toggleButton = JButton("Edit").apply { isEnabled = false }

    init {
        border = BorderFactory.createMatteBorder(0, 1, 0, 0, JBColor.border())

        applyColors()

        cards.add(JBScrollPane(editorPane).also { it.border = JBUI.Borders.empty() }, "preview")
        cards.add(JBScrollPane(editArea).also { it.border = JBUI.Borders.empty() }, "edit")

        val toolbar = JPanel(FlowLayout(FlowLayout.LEFT, 6, 4))
        toolbar.add(toggleButton)

        add(toolbar, BorderLayout.NORTH)
        add(cards, BorderLayout.CENTER)

        toggleButton.addActionListener { if (isEditing) saveAndPreview() else switchToEdit() }

        project.messageBus.connect(this).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this)
        updateContent(FileEditorManager.getInstance(project).selectedFiles.firstOrNull())
    }

    private fun switchToEdit() {
        val vFile = currentFile ?: return
        val docFile = runReadAction {
            PsiManager.getInstance(project).findFile(vFile)?.let { NaturalDocUtils.docFileFor(it) }
        } ?: return
        if (!docFile.exists()) {
            docFile.parentFile.mkdirs()
            docFile.writeText("# ${vFile.nameWithoutExtension}\n\n")
        }
        editArea.text = docFile.readText()
        editArea.caretPosition = 0
        (cards.layout as CardLayout).show(cards, "edit")
        isEditing = true
        toggleButton.text = "Save"
    }

    private fun saveCurrentEdits(): String? {
        val vFile = currentFile ?: return null
        val markdown = editArea.text
        val docFile = runReadAction {
            PsiManager.getInstance(project).findFile(vFile)?.let { NaturalDocUtils.docFileFor(it) }
        } ?: return null
        docFile.parentFile.mkdirs()
        docFile.writeText(markdown)
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(docFile)
        return markdown
    }

    private fun saveAndPreview() {
        val markdown = saveCurrentEdits() ?: return
        val html = if (markdown.isBlank()) {
            "<i>No docs added for this file.</i><br><br>Right-click the file and choose <b>Go to NaturalDoc</b> to add documentation."
        } else {
            NaturalDocUtils.markdownToHtml(markdown)
        }
        showPreview(html)
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        if (isEditing) saveCurrentEdits()
        updateContent(event.newFile)
    }

    private fun updateContent(virtualFile: VirtualFile?) {
        currentFile = virtualFile
        if (virtualFile == null || virtualFile.fileType !is NaturalFileType) {
            showPreview("<i>Open a Natural file to see its documentation.</i>")
            toggleButton.isEnabled = false
            return
        }
        toggleButton.isEnabled = true
        val html = runReadAction {
            PsiManager.getInstance(project).findFile(virtualFile)
                ?.let { NaturalDocUtils.renderDoc(it) }
                ?: "<i>Could not resolve file.</i>"
        }
        showPreview(html)
    }

    private fun showPreview(html: String) {
        val bg = colorToHex(UIUtil.getPanelBackground())
        val fg = colorToHex(UIUtil.getLabelForeground())
        editorPane.background = UIUtil.getPanelBackground()
        editorPane.text = "<html><body style='background-color:$bg;color:$fg;padding:12px 16px;'>$html</body></html>"
        editorPane.caretPosition = 0
        (cards.layout as CardLayout).show(cards, "preview")
        isEditing = false
        toggleButton.text = "Edit"
    }

    private fun applyColors() {
        val bg = UIUtil.getPanelBackground()
        background = bg
        editorPane.background = bg
        editArea.background = scheme.defaultBackground
        editArea.foreground = scheme.defaultForeground
        editArea.font = Font(scheme.editorFontName, Font.PLAIN, scheme.editorFontSize)
    }

    private fun colorToHex(c: Color) = "#%02x%02x%02x".format(c.red, c.green, c.blue)

    override fun dispose() {}
}
