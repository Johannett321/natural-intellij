package com.appweb.natural.intellij.mapeditor

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorStateLevel
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBSplitter
import com.intellij.ui.components.JBScrollPane
import java.awt.*
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.IOException
import javax.swing.*

class NaturalMapEditor(
    private val project: Project,
    private val virtualFile: VirtualFile
) : UserDataHolderBase(), FileEditor {

    private val propertyChangeSupport = PropertyChangeSupport(this)
    private var modified = false

    private lateinit var canvas: NaturalMapCanvas
    private lateinit var propertiesPanel: NaturalMapPropertiesPanel
    private val mainComponent: JComponent = buildUI()

    private fun buildUI(): JComponent {
        val content = virtualFile.contentsToByteArray().toString(Charsets.UTF_8)
        val model = NaturalMapParser.parse(content)

        canvas = NaturalMapCanvas(model)
        propertiesPanel = NaturalMapPropertiesPanel()

        canvas.addSelectionListener { elem ->
            val multiCount = canvas.selectedElements.size
            if (elem == null && multiCount > 1) propertiesPanel.showMultiSelection(multiCount)
            else propertiesPanel.showElement(elem)
        }

        canvas.addModelChangeListener {
            markModified()
        }

        propertiesPanel.addBeforeChangeListener {
            canvas.saveSnapshot()
        }

        propertiesPanel.addChangeListener {
            canvas.repaint()
            markModified()
        }

        val toolbar = buildToolbar()
        val scrollPane = JBScrollPane(canvas).also {
            it.border = BorderFactory.createEmptyBorder()
        }

        val splitter = JBSplitter(false, 0.72f).also {
            it.firstComponent = scrollPane
            it.secondComponent = propertiesPanel
            it.dividerWidth = 4
        }

        val root = JPanel(BorderLayout())
        root.add(toolbar, BorderLayout.NORTH)
        root.add(splitter, BorderLayout.CENTER)
        return root
    }

    private fun buildToolbar(): JPanel {
        val bar = JPanel(FlowLayout(FlowLayout.LEFT, 4, 2))

        val addLabelBtn = JButton("+ Label")
        addLabelBtn.toolTipText = "Add a static text label to the map"
        addLabelBtn.addActionListener {
            val text = JOptionPane.showInputDialog(mainComponent, "Label text:", "Add Label", JOptionPane.PLAIN_MESSAGE)
            if (!text.isNullOrBlank()) {
                canvas.addLabel(text)
                markModified()
            }
        }

        val addFieldBtn = JButton("+ Field")
        addFieldBtn.toolTipText = "Add a data field to the map"
        addFieldBtn.addActionListener {
            val panel = JPanel(GridLayout(2, 2, 4, 4))
            val nameField = JTextField(12)
            val lenField = JTextField("8", 4)
            panel.add(JLabel("Variable name:")); panel.add(nameField)
            panel.add(JLabel("Display length:")); panel.add(lenField)
            val result = JOptionPane.showConfirmDialog(mainComponent, panel, "Add Field",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
            if (result == JOptionPane.OK_OPTION && nameField.text.isNotBlank()) {
                val len = lenField.text.toIntOrNull() ?: 8
                canvas.addField(nameField.text.trim().uppercase(), len)
                markModified()
            }
        }

        val deleteBtn = JButton("Delete")
        deleteBtn.toolTipText = "Delete the selected element"
        deleteBtn.addActionListener {
            canvas.deleteSelected()
            markModified()
        }

        val saveBtn = JButton("Save")
        saveBtn.toolTipText = "Save changes to file"
        saveBtn.addActionListener { saveToFile() }

        bar.add(addLabelBtn)
        bar.add(addFieldBtn)
        bar.add(deleteBtn)
        bar.add(JSeparator(SwingConstants.VERTICAL))
        bar.add(saveBtn)

        return bar
    }

    private fun markModified() {
        if (!modified) {
            modified = true
            propertyChangeSupport.firePropertyChange("modified", false, true)
        }
    }

    fun saveToFile() {
        try {
            val content = NaturalMapWriter.write(canvas.getModel())
            ApplicationManager.getApplication().runWriteAction {
                virtualFile.setBinaryContent(content.toByteArray(Charsets.UTF_8))
            }
            modified = false
            propertyChangeSupport.firePropertyChange("modified", true, false)
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(mainComponent, "Save failed: ${e.message}",
                "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    override fun getComponent(): JComponent = mainComponent
    override fun getPreferredFocusedComponent(): JComponent = canvas
    override fun getName(): String = "Map Editor"
    override fun getState(level: FileEditorStateLevel): FileEditorState = FileEditorState { _, _ -> true }
    override fun setState(state: FileEditorState) {}
    override fun isModified(): Boolean = modified
    override fun isValid(): Boolean = virtualFile.isValid
    override fun addPropertyChangeListener(listener: PropertyChangeListener) =
        propertyChangeSupport.addPropertyChangeListener(listener)
    override fun removePropertyChangeListener(listener: PropertyChangeListener) =
        propertyChangeSupport.removePropertyChangeListener(listener)
    override fun dispose() {}
    override fun getCurrentLocation(): FileEditorLocation? = null
    override fun getFile(): VirtualFile = virtualFile
}
