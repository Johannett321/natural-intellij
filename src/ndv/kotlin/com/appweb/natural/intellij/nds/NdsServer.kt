package com.appweb.natural.intellij.nds

/** A configured NDV server endpoint. The password is stored separately in [PasswordSafe]. */
data class NdsServer(
    var id: String = java.util.UUID.randomUUID().toString(),
    var name: String = "",
    var host: String = "localhost",
    var port: Int = 2700,
    var user: String = "",
    var logonLibrary: String = "SYSTEM",
) {
    val displayName: String
        get() = if (name.isNotBlank()) name else "$user@$host:$port"
}
