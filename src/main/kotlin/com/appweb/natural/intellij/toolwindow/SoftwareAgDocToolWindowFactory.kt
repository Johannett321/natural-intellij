package com.appweb.natural.intellij.toolwindow

import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefApp
import com.intellij.ui.jcef.JBCefBrowser
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

const val SAG_DOC_BASE_URL = "https://documentation.softwareag.com/natural/nat911win/sm/"
const val SAG_DOC_DEFAULT_URL = "${SAG_DOC_BASE_URL}sm-over.htm"

class SoftwareAgDocToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = SoftwareAgDocPanel()
        Disposer.register(toolWindow.disposable, panel)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

class SoftwareAgDocPanel : JPanel(BorderLayout()), Disposable {

    private val browser: JBCefBrowser? = if (JBCefApp.isSupported()) {
        JBCefBrowser(SAG_DOC_DEFAULT_URL)
    } else null

    init {
        if (browser != null) {
            val b = browser
            val backButton = JButton(AllIcons.Actions.Back).apply {
                toolTipText = "Back"
                isFocusable = false
                addActionListener { b.cefBrowser.goBack() }
            }
            val forwardButton = JButton(AllIcons.Actions.Forward).apply {
                toolTipText = "Forward"
                isFocusable = false
                addActionListener { b.cefBrowser.goForward() }
            }
            val toolbar = JPanel(FlowLayout(FlowLayout.LEFT, 4, 2))
            toolbar.add(backButton)
            toolbar.add(forwardButton)
            add(toolbar, BorderLayout.NORTH)
            add(b.component, BorderLayout.CENTER)


        } else {
            add(
                JLabel("JCEF browser not available in this IDE instance", SwingConstants.CENTER),
                BorderLayout.CENTER
            )
        }
    }

    override fun dispose() {
        browser?.dispose()
    }
}
