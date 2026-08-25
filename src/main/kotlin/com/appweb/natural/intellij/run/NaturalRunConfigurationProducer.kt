package com.appweb.natural.intellij.run

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class NaturalRunConfigurationProducer : LazyRunConfigurationProducer<NaturalRunConfiguration>() {

    override fun getConfigurationFactory(): ConfigurationFactory =
        NaturalRunConfigurationType.getInstance().configurationFactories[0]

    override fun setupConfigurationFromContext(
        configuration: NaturalRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        if (file.extension?.uppercase() !in setOf("NSP", "NSN")) return false
        if (configuration.name == "Natural Program") {
            configuration.name = "Natural (${configuration.host.ifBlank { "unconfigured" }})"
        }
        return true
    }

    override fun isConfigurationFromContext(
        configuration: NaturalRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        return file.extension?.uppercase() in setOf("NSP", "NSN")
    }
}
