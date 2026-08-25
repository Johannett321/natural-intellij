package com.appweb.natural.intellij.run

import com.appweb.natural.intellij.NaturalIcons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class NaturalRunConfigurationType : ConfigurationTypeBase(
    ID, "Natural Program", "Run a Natural program via SSH", NaturalIcons.PROGRAM
) {
    companion object {
        const val ID = "NaturalRunConfiguration"

        fun getInstance(): NaturalRunConfigurationType =
            ConfigurationTypeUtil.findConfigurationType(NaturalRunConfigurationType::class.java)
    }

    init {
        addFactory(NaturalRunConfigurationFactory(this))
    }
}

class NaturalRunConfigurationFactory(type: NaturalRunConfigurationType) : ConfigurationFactory(type) {
    companion object {
        const val ID = "NaturalRunConfigurationFactory"
    }

    override fun getId() = ID

    override fun createTemplateConfiguration(project: Project): RunConfiguration =
        NaturalRunConfiguration(project, this, "Natural Program")
}
