package com.appweb.natural.intellij.nds

/** A configured NDV server endpoint. The password is stored separately in [PasswordSafe]. */
data class NdsServer(
    var id: String = java.util.UUID.randomUUID().toString(),
    var name: String = "",
    var host: String = "localhost",
    var port: Int = 2700,
    var user: String = "",
    var logonLibrary: String = "SYSTEM",
    /** Charset the server uses for object names and other strings, e.g. ISO-8859-1 or UTF-8. */
    var encoding: String = NdsClient.DEFAULT_ENCODING,
) {
    val displayName: String
        get() = if (name.isNotBlank()) name else "$user@$host:$port"
}
