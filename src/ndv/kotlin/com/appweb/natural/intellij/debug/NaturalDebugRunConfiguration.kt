package com.appweb.natural.intellij.debug

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationError
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializer
import org.jdom.Element

class NaturalDebugRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String,
) : LocatableConfigurationBase<RunConfigurationOptions>(project, factory, name) {

    /** Id of an [com.appweb.natural.intellij.nds.NdsServer] in NdsServerSettings. */
    var serverId: String = ""
    var library: String = ""
    var obj: String = ""
    var parameter: String = ""

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> =
        NaturalDebugConfigurationEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState =
        NaturalDebugRunProfileState(environment, this)

    override fun checkConfiguration() {
        if (serverId.isBlank()) throw RuntimeConfigurationError("Select an NDS server")
        if (library.isBlank()) throw RuntimeConfigurationError("Library is required")
        if (obj.isBlank()) throw RuntimeConfigurationError("Object name is required")
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        element.setAttribute("serverId", serverId)
        element.setAttribute("library", library)
        element.setAttribute("object", obj)
        element.setAttribute("parameter", parameter)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        serverId = element.getAttributeValue("serverId") ?: ""
        library = element.getAttributeValue("library") ?: ""
        obj = element.getAttributeValue("object") ?: ""
        parameter = element.getAttributeValue("parameter") ?: ""
    }
}
