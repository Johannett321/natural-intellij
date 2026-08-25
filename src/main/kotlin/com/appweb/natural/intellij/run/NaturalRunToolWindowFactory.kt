package com.appweb.natural.intellij.run

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import javax.swing.JPanel

class NaturalRunToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // content is added programmatically when a run starts
    }

    override fun shouldBeAvailable(project: Project) = false
}
