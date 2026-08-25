package com.appweb.natural.intellij.debug

import com.appweb.natural.intellij.NaturalIcons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project
import javax.swing.Icon

class NaturalDebugConfigurationType : ConfigurationType {
    override fun getDisplayName(): String = "Natural Debug"
    override fun getConfigurationTypeDescription(): String =
        "Debug a Natural object on a Natural Development Server"
    override fun getIcon(): Icon = NaturalIcons.PROGRAM
    override fun getId(): String = "Natural.Debug"

    private val factory = object : ConfigurationFactory(this) {
        override fun getId(): String = "Natural.Debug.Factory"
        override fun createTemplateConfiguration(project: Project): RunConfiguration =
            NaturalDebugRunConfiguration(project, this, "Natural Debug")
    }

    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(factory)
}
