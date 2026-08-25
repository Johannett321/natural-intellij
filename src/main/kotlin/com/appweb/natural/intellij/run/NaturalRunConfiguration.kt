package com.appweb.natural.intellij.run

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import org.jdom.Element
import java.util.UUID

class NaturalRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<RunConfigurationOptions>(project, factory, name) {

    var credentialId: String = UUID.randomUUID().toString()
    var username: String = ""
    var host: String = ""
    var port: Int = 22
    var parms: String = ""

    var password: String
        get() {
            val attrs = CredentialAttributes(generateServiceName("NaturalPlugin", credentialId))
            return PasswordSafe.instance.getPassword(attrs) ?: ""
        }
        set(value) {
            val attrs = CredentialAttributes(generateServiceName("NaturalPlugin", credentialId))
            PasswordSafe.instance.set(attrs, if (value.isNotEmpty()) Credentials(username, value) else null)
        }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> =
        NaturalRunConfigurationEditor(project)

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState =
        NaturalRunProfileState(environment, this)

    override fun checkConfiguration() {
        if (host.isBlank()) throw RuntimeConfigurationError("Server host is required")
        if (username.isBlank()) throw RuntimeConfigurationError("Username is required")
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        credentialId = element.getAttributeValue("credentialId") ?: UUID.randomUUID().toString()
        username = element.getAttributeValue("username") ?: ""
        host = element.getAttributeValue("host") ?: ""
        port = element.getAttributeValue("port")?.toIntOrNull() ?: 22
        parms = element.getAttributeValue("parms") ?: ""
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        element.setAttribute("credentialId", credentialId)
        element.setAttribute("username", username)
        element.setAttribute("host", host)
        element.setAttribute("port", port.toString())
        element.setAttribute("parms", parms)
    }
}
