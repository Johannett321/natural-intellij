package com.appweb.natural.intellij.debug

import com.appweb.natural.intellij.nds.NdsServer
import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class NaturalDebugConfigurationEditor : SettingsEditor<NaturalDebugRunConfiguration>() {

    private val serverCombo = ComboBox<NdsServer>()
    private val libraryField = JBTextField()
    private val objectField = JBTextField()
    private val parameterField = JBTextField()

    private val panel: JPanel by lazy {
        // Refresh server list each time the editor is built.
        serverCombo.removeAllItems()
        NdsServerSettings.getInstance().servers.forEach(serverCombo::addItem)
        FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Server:"), serverCombo, 1, false)
            .addLabeledComponent(JBLabel("Library:"), libraryField, 1, false)
            .addLabeledComponent(JBLabel("Object:"), objectField, 1, false)
            .addLabeledComponent(JBLabel("Natural parameters:"), parameterField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun createEditor(): JComponent = panel

    override fun resetEditorFrom(s: NaturalDebugRunConfiguration) {
        val servers = NdsServerSettings.getInstance().servers
        serverCombo.removeAllItems()
        servers.forEach(serverCombo::addItem)
        servers.firstOrNull { it.id == s.serverId }?.let { serverCombo.selectedItem = it }
        libraryField.text = s.library
        objectField.text = s.obj
        parameterField.text = s.parameter
    }

    override fun applyEditorTo(s: NaturalDebugRunConfiguration) {
        val selected = serverCombo.selectedItem as? NdsServer
        s.serverId = selected?.id ?: ""
        s.library = libraryField.text.trim()
        s.obj = objectField.text.trim()
        s.parameter = parameterField.text.trim()
    }
}
