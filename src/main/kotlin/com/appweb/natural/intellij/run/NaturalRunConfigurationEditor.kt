package com.appweb.natural.intellij.run

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

class NaturalRunConfigurationEditor(private val project: Project) : SettingsEditor<NaturalRunConfiguration>() {

    private val hostField = JBTextField()
    private val portSpinner = JSpinner(SpinnerNumberModel(22, 1, 65535, 1))
    private val usernameField = JBTextField()
    private val passwordField = JBPasswordField()
    private val parmsField = JBTextField()

    override fun resetEditorFrom(config: NaturalRunConfiguration) {
        hostField.text = config.host
        portSpinner.value = config.port
        usernameField.text = config.username
        passwordField.text = config.password
        parmsField.text = config.parms
    }

    override fun applyEditorTo(config: NaturalRunConfiguration) {
        config.host = hostField.text.trim()
        config.port = portSpinner.value as Int
        config.username = usernameField.text.trim()
        config.password = String(passwordField.password)
        config.parms = parmsField.text.trim()
    }

    override fun createEditor(): JComponent {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Host:"), hostField, true)
            .addLabeledComponent(JBLabel("Port:"), portSpinner, true)
            .addLabeledComponent(JBLabel("Username:"), usernameField, true)
            .addLabeledComponent(JBLabel("Password:"), passwordField, true)
            .addLabeledComponent(JBLabel("Parameters (parm=):"), parmsField, true)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}
