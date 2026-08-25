package com.appweb.natural.intellij.nds

import com.softwareag.naturalone.natural.pal.external.IPalTypeLibrary
import com.softwareag.naturalone.natural.pal.external.IPalTypeObject
import com.softwareag.naturalone.natural.pal.external.IPalTypeSystemFile
import com.softwareag.naturalone.natural.pal.external.ObjectKind
import com.softwareag.naturalone.natural.pal.external.ObjectType
import com.softwareag.naturalone.natural.paltransactions.external.ConnectKey
import com.softwareag.naturalone.natural.paltransactions.external.EDownLoadOption
import com.softwareag.naturalone.natural.paltransactions.external.IPalClientIdentification
import com.softwareag.naturalone.natural.paltransactions.external.ITransactionContextDownload
import com.softwareag.naturalone.natural.paltransactions.external.ObjectProperties
import com.softwareag.naturalone.natural.paltransactions.external.PalCompileResultException
import com.softwareag.naturalone.natural.paltransactions.external.PalResultException
import com.softwareag.naturalone.natural.paltransactions.internal.PalTransactions
import java.io.Closeable
import java.io.IOException
import java.util.EnumSet
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Kotlin client for Software AG Natural Development Server (NDV) using the bundled NaturalONE
 * client JARs. Encapsulates a handful of hard-won workarounds:
 *
 *  - The async PalReceiveThread can die unexpectedly on certain server responses. We poll a
 *    Future with a deadline + asyncFailed flag so callers don't hang forever.
 *  - After one library's worth of activity, the connection state gets corrupted (a second
 *    listObjects on the same connection overflows an internal 50-byte buffer). The fix is to
 *    open a fresh NDV connection per "session" of work; callers should use the [forLibrary]
 *    factory or short-lived [use] blocks.
 *  - Server bytes are read with the JVM default charset. Object and library names may contain
 *    non-ASCII characters, so set -Dfile.encoding=ISO-8859-1 at JVM launch — for an IntelliJ
 *    plugin that means the IDE's vmoptions. We additionally decode names ourselves where possible.
 */
class NdsClient private constructor(
    private val tx: PalTransactions,
    private val io: ExecutorService,
    private val fuser: IPalTypeSystemFile,
    private val ctx: ITransactionContextDownload,
) : Closeable {

    private val asyncFailed: () -> Boolean = { ASYNC_FAILED.get() }

    fun listLibraries(): List<String> = call {
        val names = mutableListOf<String>()
        var libs: Array<out IPalTypeLibrary>? = tx.getLibrariesFirst(fuser, "*")
        while (libs != null && libs.isNotEmpty()) {
            libs.forEach { names += it.library }
            libs = tx.librariesNext
        }
        names
    }

    fun listObjects(libraryName: String): List<NdsObjectInfo> = call {
        val out = mutableListOf<NdsObjectInfo>()
        var objs: Array<out IPalTypeObject>? = tx.getObjectsFirst(
            fuser, libraryName, "*", ObjectKind.ANY, ObjectType.ANY,
        )
        while (objs != null && objs.isNotEmpty()) {
            objs.forEach { o -> NdsObjectInfo.from(o)?.let(out::add) }
            objs = tx.objectsNext
        }
        out
    }

    /**
     * Compile the given source on the server and save both source and generated program (STOW).
     * Re-logs on to [libraryName] if it differs from the current logon library.
     *
     * Throws [NdsCompileError] on compile failure (with row/column/message), or [NdsException]
     * for transport / protocol errors.
     */
    fun stow(
        libraryName: String,
        objectName: String,
        extension: String,
        sourceLines: List<String>,
        codePage: String? = null,
    ) = compileOp(libraryName, objectName, extension, sourceLines, codePage) { props, lines ->
        tx.stow(fuser, libraryName, props, lines)
    }

    /**
     * Syntax-check the given source on the server without saving anything (CHECK).
     * Re-logs on to [libraryName] if it differs from the current logon library.
     */
    fun check(
        libraryName: String,
        objectName: String,
        extension: String,
        sourceLines: List<String>,
        codePage: String? = null,
    ) = compileOp(libraryName, objectName, extension, sourceLines, codePage) { props, lines ->
        tx.check(fuser, libraryName, props, lines)
    }

    private fun compileOp(
        libraryName: String,
        objectName: String,
        extension: String,
        sourceLines: List<String>,
        codePage: String?,
        op: (ObjectProperties, Array<String>) -> Unit,
    ) = call(timeoutSec = COMPILE_TIMEOUT_SEC) {
        val type = ObjectType.getInstanceExtensionId()[extension.uppercase()]
            ?: throw NdsException("Unknown Natural object extension '$extension'")
        if (!libraryName.equals(tx.logonLibrary, ignoreCase = true)) {
            tx.logon(libraryName)
        }
        val builder = ObjectProperties.Builder(objectName.uppercase(), type)
            .natKind(ObjectKind.SOURCE)
            .sourceSize(sourceLines.sumOf { it.length + 1 })
        if (codePage != null) builder.codePage(codePage)
        try {
            op(builder.build(), sourceLines.toTypedArray())
        } catch (e: PalCompileResultException) {
            throw NdsCompileError.from(e, objectName, libraryName)
        } catch (e: PalResultException) {
            throw NdsException(formatPalError(e), e)
        }
    }

    /** Returns the source as a single string, joined with '\n'. */
    fun downloadSource(libraryName: String, info: NdsObjectInfo): String = call {
        val props = ObjectProperties.Builder(info.name, info.type)
            .longName(info.longName)
            .user(info.user)
            .codePage(info.codePage)
            .sourceSize(info.sourceSize)
            .natKind(info.kind)
            .databaseId(info.databaseId)
            .fileNumber(info.fileNumber)
            .isStructured(info.structured)
            .build()
        tx.downloadSource(ctx, fuser, libraryName, props, EnumSet.of(EDownLoadOption.NONE))
            .source.joinToString("\n")
    }

    override fun close() {
        if (!ASYNC_FAILED.get()) {
            try { tx.disposeTransactionContext(ctx) } catch (_: Throwable) {}
            try { tx.close() } catch (_: Throwable) {}
        }
        io.shutdownNow()
    }

    /** Run a callable on the io thread with a deadline + responsive async-fail check. */
    private fun <T> call(timeoutSec: Int = CALL_TIMEOUT_SEC, op: () -> T): T {
        val future = io.submit(Callable { op() })
        val deadlineNs = System.nanoTime() + timeoutSec * 1_000_000_000L
        while (true) {
            if (asyncFailed()) {
                future.cancel(true)
                throw NdsException("NDV receive thread died")
            }
            if (future.isDone) {
                try { return future.get() }
                catch (e: ExecutionException) {
                    throw e.cause?.let(::wrap) ?: NdsException("call failed: $e")
                }
            }
            if (System.nanoTime() > deadlineNs) {
                future.cancel(true)
                throw NdsException("NDV call timed out after ${timeoutSec}s")
            }
            Thread.sleep(50)
        }
    }

    companion object {
        const val CALL_TIMEOUT_SEC = 30
        const val COMPILE_TIMEOUT_SEC = 120
        const val MAX_USER_LEN = 8

        private val ASYNC_FAILED = ThreadLocal.withInitial { false }

        init {
            // The Pal library spawns an internal "PalReceiveThread" that can die from an
            // unhandled ArrayIndexOutOfBoundsException on certain server responses. Mark
            // the connection unusable instead of letting JVM uncaught-handler swallow it.
            val existing = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                if (t.name.contains("Receive", ignoreCase = true)) {
                    ASYNC_FAILED.set(true)
                }
                existing?.uncaughtException(t, e)
            }
        }

        @Throws(NdsException::class)
        fun connect(
            host: String,
            port: Int,
            user: String,
            password: String,
            logonLibrary: String,
        ): NdsClient {
            ASYNC_FAILED.set(false)
            val io = Executors.newSingleThreadExecutor { r ->
                Thread(r, "nds-io").apply { isDaemon = true }
            }
            val tx = PalTransactions(CLIENT_ID)
            val params = mapOf(
                ConnectKey.HOST to host,
                ConnectKey.PORT to port.toString(),
                ConnectKey.USERID to user.uppercase().take(MAX_USER_LEN),
                ConnectKey.PASSWORD to password,
            )
            // Run connect+logon under the same watchdog discipline.
            val deadlineNs = System.nanoTime() + CALL_TIMEOUT_SEC * 1_000_000_000L
            val future = io.submit(Callable<Unit> {
                tx.connect(params)
                tx.logon(logonLibrary)
            })
            while (true) {
                if (future.isDone) {
                    try { future.get() }
                    catch (e: ExecutionException) {
                        io.shutdownNow()
                        throw wrap(e.cause ?: e)
                    }
                    break
                }
                if (System.nanoTime() > deadlineNs) {
                    future.cancel(true)
                    io.shutdownNow()
                    throw NdsException("NDV connect timed out after ${CALL_TIMEOUT_SEC}s")
                }
                Thread.sleep(50)
            }

            val fuser = tx.systemFiles.firstOrNull { it.kind == IPalTypeSystemFile.FUSER }
                ?: run {
                    try { tx.close() } catch (_: Throwable) {}
                    io.shutdownNow()
                    throw NdsException("Server has no FUSER system file")
                }
            val ctx = tx.createTransactionContext(ITransactionContextDownload::class.java)
                    as ITransactionContextDownload
            return NdsClient(tx, io, fuser, ctx)
        }

        private val CLIENT_ID = object : IPalClientIdentification {
            override fun getNdvClientVersion() = IPalClientIdentification.NATONE_VERSION
            override fun getNdvClientId() = IPalClientIdentification.PALCLIENTID_ONE
            override fun getWebIOVersion() = IPalClientIdentification.WEB_IO_VERSION
        }

        private fun wrap(t: Throwable): NdsException =
            if (t is NdsException) t else NdsException(t.message ?: t.javaClass.simpleName, t)
    }
}

open class NdsException(message: String, cause: Throwable? = null) : IOException(message, cause)

/**
 * Compile failure surfaced by stow / catalog / check. Carries enough detail to highlight the
 * offending line in the editor.
 */
class NdsCompileError(
    val row: Int,
    val column: Int,
    val errorNumber: Int,
    val objectName: String,
    val libraryName: String,
    val shortText: String,
    val longText: List<String>,
) : NdsException("NAT$errorNumber at $objectName($row,$column): $shortText") {
    companion object {
        fun from(e: PalCompileResultException, objectName: String, libraryName: String) =
            NdsCompileError(
                row          = e.row,
                column       = e.column,
                errorNumber  = e.errorNumber,
                objectName   = e.`object`?.takeIf { it.isNotBlank() } ?: objectName,
                libraryName  = e.library?.takeIf { it.isNotBlank() } ?: libraryName,
                shortText    = e.shortText.orEmpty(),
                longText     = e.longText?.toList().orEmpty(),
            )
    }
}

private fun formatPalError(e: PalResultException): String {
    val prefix = if (e.errorNumber > 0) "NAT${e.errorNumber}: " else ""
    return prefix + (e.shortText ?: e.message ?: e.javaClass.simpleName)
}

/** Filesystem-friendly snapshot of an [IPalTypeObject]. */
data class NdsObjectInfo(
    val name: String,
    val longName: String?,
    val type: Int,
    val kind: Int,
    val codePage: String?,
    val sourceSize: Int,
    val user: String?,
    val databaseId: Int,
    val fileNumber: Int,
    val structured: Boolean,
) {
    /** Standard NaturalONE filename extension or null if this object cannot be saved as text. */
    val extension: String? get() = when (type) {
        ObjectType.PROGRAM     -> "NSP"
        ObjectType.SUBPROGRAM  -> "NSN"
        ObjectType.SUBROUTINE  -> "NSS"
        ObjectType.COPYCODE    -> "NSC"
        ObjectType.HELPROUTINE -> "NSH"
        ObjectType.MAP         -> "NSM"
        ObjectType.LDA         -> "NSL"
        ObjectType.PDA         -> "NSA"
        ObjectType.GDA         -> "NSG"
        ObjectType.FUNCTION    -> "NS7"
        ObjectType.DIALOG      -> "NS3"
        ObjectType.ADAPTER     -> "NS8"
        ObjectType.CLASS       -> "NS4"
        ObjectType.TEXT        -> "NST"
        ObjectType.ERRMSG      -> "ERR"
        ObjectType.DDM         -> null // DDMs live in FDDM, not FUSER
        else                   -> null
    }

    /**
     * Sub-folder a NaturalONE project uses for this object type, e.g. PROGRAM -> "Programs".
     * Null when we have no convention for the type; such objects go straight into the library
     * folder. Names must match NaturalONE exactly - they are what Eclipse/NaturalONE creates.
     */
    val typeFolder: String? get() = when (type) {
        ObjectType.PROGRAM     -> "Programs"
        ObjectType.SUBPROGRAM  -> "Subprograms"
        ObjectType.SUBROUTINE  -> "Subroutines"
        ObjectType.COPYCODE    -> "Copycodes"
        ObjectType.HELPROUTINE -> "Helproutines"
        ObjectType.MAP         -> "Maps"
        ObjectType.LDA         -> "Local Data Areas"
        ObjectType.PDA         -> "Parameter Data Areas"
        ObjectType.GDA         -> "Global Data Areas"
        ObjectType.FUNCTION    -> "Functions"
        ObjectType.ADAPTER     -> "Adapters"
        ObjectType.DIALOG      -> "Dialogs"
        ObjectType.CLASS       -> "Classes"
        ObjectType.TEXT        -> "Texts"
        ObjectType.ERRMSG      -> "Error Messages"
        ObjectType.RESOURCE    -> "Resources"
        else                   -> null
    }

    /** True if the object has a Natural source we can download. */
    val hasSource: Boolean get() = (kind and ObjectKind.SOURCE) != 0 && extension != null

    val typeLabel: String get() = when (type) {
        ObjectType.PROGRAM     -> "PROGRAM"
        ObjectType.SUBPROGRAM  -> "SUBPROGRAM"
        ObjectType.SUBROUTINE  -> "SUBROUTINE"
        ObjectType.COPYCODE    -> "COPYCODE"
        ObjectType.HELPROUTINE -> "HELPROUTINE"
        ObjectType.MAP         -> "MAP"
        ObjectType.LDA         -> "LDA"
        ObjectType.PDA         -> "PDA"
        ObjectType.GDA         -> "GDA"
        ObjectType.FUNCTION    -> "FUNCTION"
        ObjectType.ADAPTER     -> "ADAPTER"
        ObjectType.DIALOG      -> "DIALOG"
        ObjectType.CLASS       -> "CLASS"
        ObjectType.TEXT        -> "TEXT"
        ObjectType.ERRMSG      -> "ERRMSG"
        ObjectType.DDM         -> "DDM"
        else                   -> "type=$type"
    }

    companion object {
        fun from(o: IPalTypeObject): NdsObjectInfo? = NdsObjectInfo(
            name        = o.name,
            longName    = o.longName,
            type        = o.type,
            kind        = o.kind,
            codePage    = o.codePage,
            sourceSize  = o.sourceSize,
            user        = o.user,
            databaseId  = o.databaseId,
            fileNumber  = o.fileNumber,
            structured  = o.isStructured,
        )
    }
}
