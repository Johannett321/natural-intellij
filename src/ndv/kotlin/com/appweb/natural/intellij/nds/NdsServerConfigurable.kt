package com.appweb.natural.intellij.nds

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.AnActionButton
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.DefaultListModel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField

/**
 * Application-level Preferences page: Settings → Tools → Natural Servers.
 * Lets the user add/edit/remove NDV server entries; passwords go to PasswordSafe.
 */
class NdsServerConfigurable : Configurable {

    private val listModel = DefaultListModel<NdsServer>()
    private val list = JBList(listModel).apply { cellRenderer = ServerCellRenderer() }
    private val pendingPasswords = mutableMapOf<String, CharArray>()
    private val pendingPasswordClears = mutableSetOf<String>()
    private var rootPanel: JPanel? = null

    override fun getDisplayName(): String = "Natural Servers"

    override fun createComponent(): JComponent {
        reset()
        val decorator = ToolbarDecorator.createDecorator(list)
            .setAddAction { _ -> add() }
            .setEditAction { _ -> edit() }
            .setRemoveAction { _ -> remove() }
            .disableUpDownActions()
        return JPanel(BorderLayout()).also {
            it.add(decorator.createPanel(), BorderLayout.CENTER)
            rootPanel = it
        }
    }

    override fun isModified(): Boolean {
        val stored = NdsServerSettings.getInstance().servers
        if (stored.size != listModel.size()) return true
        for (i in 0 until listModel.size()) {
            if (listModel[i] != stored[i]) return true
        }
        return pendingPasswords.isNotEmpty() || pendingPasswordClears.isNotEmpty()
    }

    override fun apply() {
        val settings = NdsServerSettings.getInstance()
        val list = (0 until listModel.size()).map { listModel[it] }
        settings.replaceAll(list)
        list.forEach { srv ->
            pendingPasswords[srv.id]?.let { pwd -> settings.setPassword(srv, pwd) }
            if (srv.id in pendingPasswordClears) settings.setPassword(srv, null)
        }
        pendingPasswords.clear()
        pendingPasswordClears.clear()
    }

    override fun reset() {
        listModel.clear()
        NdsServerSettings.getInstance().servers.forEach { listModel.addElement(it.copy()) }
        pendingPasswords.clear()
        pendingPasswordClears.clear()
    }

    private fun add() {
        val server = NdsServer(name = "New Server")
        val dialog = EditDialog(server, "Add Natural Server")
        if (dialog.showAndGet()) {
            val updated = dialog.resultServer
            listModel.addElement(updated)
            dialog.resultPassword?.let { pendingPasswords[updated.id] = it }
        }
    }

    private fun edit() {
        val idx = list.selectedIndex.takeIf { it >= 0 } ?: return
        val original = listModel[idx]
        val dialog = EditDialog(original.copy(), "Edit Natural Server")
        if (dialog.showAndGet()) {
            listModel.set(idx, dialog.resultServer)
            dialog.resultPassword?.let { pendingPasswords[dialog.resultServer.id] = it }
            if (dialog.passwordCleared) pendingPasswordClears += dialog.resultServer.id
        }
    }

    private fun remove() {
        val idx = list.selectedIndex.takeIf { it >= 0 } ?: return
        val srv = listModel[idx]
        if (Messages.showYesNoDialog(
                "Remove server '${srv.displayName}'?",
                "Remove Natural Server",
                Messages.getQuestionIcon(),
            ) == Messages.YES) {
            listModel.remove(idx)
            pendingPasswordClears += srv.id
        }
    }

    private class ServerCellRenderer : javax.swing.DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: javax.swing.JList<*>?, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean,
        ): java.awt.Component {
            val srv = value as? NdsServer
            val text = srv?.let { "${it.displayName}  (${it.host}:${it.port} as ${it.user})" }
                ?: value?.toString().orEmpty()
            return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus)
        }
    }

    private class EditDialog(
        private val server: NdsServer,
        title: String,
    ) : DialogWrapper(true) {
        private val nameField    = JTextField(server.name, 28)
        private val hostField    = JTextField(server.host, 28)
        private val portField    = JTextField(server.port.toString(), 6)
        private val userField    = JTextField(server.user, 12)
        private val passwordField = JPasswordField(20)

        var resultPassword: CharArray? = null; private set
        var passwordCleared: Boolean = false; private set

        val resultServer: NdsServer get() = server.copy(
            name = nameField.text.trim(),
            host = hostField.text.trim(),
            port = portField.text.trim().toIntOrNull() ?: server.port,
            user = userField.text.trim(),
            // logonLibrary is hardcoded to SYSTEM; preserved field for any pre-existing config.
        )

        init {
            this.title = title
            init()
        }

        override fun createCenterPanel(): JComponent {
            val panel = JPanel(GridBagLayout())
            val c = GridBagConstraints().apply {
                gridx = 0; gridy = 0; anchor = GridBagConstraints.WEST
                insets = Insets(4, 4, 4, 4)
            }
            fun row(label: String, field: JComponent) {
                c.gridx = 0; panel.add(JLabel(label), c)
                c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0
                panel.add(field, c)
                c.gridy++
                c.fill = GridBagConstraints.NONE; c.weightx = 0.0
            }
            row("Display name:", nameField)
            row("Host:",         hostField)
            row("Port:",         portField)
            row("User:",         userField)
            row("Password:",     passwordField)
            panel.border = JBUI.Borders.empty(8)
            return panel
        }

        override fun doOKAction() {
            val pw = passwordField.password
            if (pw.isNotEmpty()) resultPassword = pw
            else passwordCleared = false // leave existing alone
            super.doOKAction()
        }
    }
}
