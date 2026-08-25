package com.appweb.natural.intellij.nds

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "NaturalForIntelliJ.NdsServerSettings",
    storages = [Storage("naturalForIntelliJ.NdsServers.xml")],
)
@Service(Service.Level.APP)
class NdsServerSettings : PersistentStateComponent<NdsServerSettings.State> {

    class State {
        var servers: MutableList<NdsServer> = mutableListOf()
    }

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, this.state)
    }

    val servers: List<NdsServer> get() = state.servers.toList()

    fun replaceAll(newServers: List<NdsServer>) {
        state.servers = newServers.toMutableList()
    }

    fun findById(id: String): NdsServer? = state.servers.firstOrNull { it.id == id }

    fun setPassword(server: NdsServer, password: CharArray?) {
        val attrs = credentialAttributes(server.id)
        val credentials = password?.let { Credentials(server.user, it) }
        PasswordSafe.instance.set(attrs, credentials)
    }

    fun getPassword(server: NdsServer): String =
        PasswordSafe.instance.getPassword(credentialAttributes(server.id)) ?: ""

    private fun credentialAttributes(serverId: String): CredentialAttributes =
        CredentialAttributes(generateServiceName("NaturalForIntelliJ NDS", serverId))

    companion object {
        fun getInstance(): NdsServerSettings =
            ApplicationManager.getApplication().getService(NdsServerSettings::class.java)
    }
}
