package com.appweb.natural.intellij.debug

import com.appweb.natural.intellij.nds.NdsException
import com.softwareag.naturalone.natural.pal.PalTypeDbgStackFrame
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgSpy
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgSyt
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgVarContainer
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgVarDesc
import com.softwareag.naturalone.natural.pal.external.IPalTypeDbgVarValue
import com.softwareag.naturalone.natural.pal.external.IPalTypeNotify
import com.softwareag.naturalone.natural.pal.external.PalTypeDbgSpyFactory
import com.softwareag.naturalone.natural.pal.external.PalTypeDbgVarContainerFactory
import com.softwareag.naturalone.natural.pal.external.PalTypeDbgVarDescFactory
import com.softwareag.naturalone.natural.paltransactions.external.ConnectKey
import com.softwareag.naturalone.natural.paltransactions.external.IPalClientIdentification
import com.softwareag.naturalone.natural.paltransactions.external.ISuspendResult
import com.softwareag.naturalone.natural.paltransactions.external.PalResultException
import com.softwareag.naturalone.natural.paltransactions.internal.PalTransactions
import java.io.Closeable
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Debug session over a single dedicated NDV connection. Wraps the [PalTransactions] debug API
 * (debugStart, debugResume, debugStep*, spySet/Delete, getSymbolTable, getValue, modifyValue,
 * setNextStatement, debugExit) and exposes a coarse, single-threaded synchronous API to callers.
 *
 * All Pal calls are funnelled through one IO thread because the underlying receive thread is
 * shared mutable state. Methods return when the server replies (or throw on timeout / error).
 *
 * Suspension state is owned by this class: every call that hits a "suspended" condition
 * (start, resume, step*) updates [lastSuspendedFrames] / [lastSuspendedReason] so that
 * [SuspendListener] can be told what to render.
 */
class NdsDebugSession private constructor(
    private val tx: PalTransactions,
    private val io: java.util.concurrent.ExecutorService,
    private val library: String,
    private val obj: String,
    private val parameter: String,
) : Closeable {

    enum class StopReason { ENTRY, BREAKPOINT, STEP, EXCEPTION, PAUSE }

    /** Result of any operation that potentially suspends the runtime. */
    data class SuspendInfo(
        val reason: StopReason,
        val frames: List<PalTypeDbgStackFrame>,
        val terminated: Boolean,
        val errorText: String? = null,
    )

    interface Listener {
        fun onStarted(library: String, obj: String) {}
        fun onSuspended(info: SuspendInfo) {}
        fun onTerminated(reason: String?) {}
        fun onConsoleOutput(text: String) {}
        fun onError(t: Throwable) {}
    }

    /** True once the runtime has reported termination (no further pal calls valid). */
    private val runtimeTerminated = AtomicBoolean(false)
    /** True once [close] has actually torn down sockets / shut down the IO thread. */
    private val closed = AtomicBoolean(false)
    private var listener: Listener? = null
    private val installedSpies = mutableMapOf<SpyKey, IPalTypeDbgSpy>()

    fun setListener(l: Listener) { listener = l }

    /**
     * Kick off the debug session. The runtime starts in "suspended at first executable line" state
     * — server returns the initial stack frames in the suspend result. We forward that to the
     * listener as an [StopReason.ENTRY] event.
     */
    fun start(): SuspendInfo = call("start") {
        val result = tx.debugStart(library, obj, parameter)
        handleSuspend(result, StopReason.ENTRY)
    }.also { info ->
        listener?.onStarted(library, obj)
        listener?.onSuspended(info)
    }

    fun resume(): SuspendInfo = call("resume") {
        handleSuspend(tx.debugResume(), StopReason.BREAKPOINT)
    }.also { info -> dispatch(info) }

    fun stepOver(): SuspendInfo = call("stepOver") {
        handleSuspend(tx.debugStepOver(), StopReason.STEP)
    }.also { info -> dispatch(info) }

    fun stepInto(): SuspendInfo = call("stepInto") {
        handleSuspend(tx.debugStepInto(), StopReason.STEP)
    }.also { info -> dispatch(info) }

    fun stepReturn(): SuspendInfo = call("stepReturn") {
        handleSuspend(tx.debugStepReturn(), StopReason.STEP)
    }.also { info -> dispatch(info) }

    /** Set a temporary line breakpoint, resume, then delete the breakpoint when we suspend again. */
    fun runToLine(library: String, obj: String, line: Int): SuspendInfo = call("runToLine") {
        val spy = createLineSpy(library, obj, line)
        val installed = tx.spySet(spy)
        try {
            handleSuspend(tx.debugResume(), StopReason.STEP)
        } finally {
            try { tx.spyDelete(installed) } catch (_: Throwable) {}
        }
    }.also { info -> dispatch(info) }

    /** Idempotent: installs at most one breakpoint per (library, object, line). */
    fun setBreakpoint(library: String, obj: String, line: Int): Unit = call("setBreakpoint") {
        val key = SpyKey(library.uppercase(), obj.uppercase(), line)
        if (installedSpies.containsKey(key)) return@call
        val installed = tx.spySet(createLineSpy(library, obj, line))
        installedSpies[key] = installed
    }

    fun removeBreakpoint(library: String, obj: String, line: Int): Unit = call("removeBreakpoint") {
        val key = SpyKey(library.uppercase(), obj.uppercase(), line)
        val spy = installedSpies.remove(key) ?: return@call
        try { tx.spyDelete(spy) } catch (_: Throwable) {}
    }

    /** Read the symbol table for the given scope of [frame]. */
    fun getSymbolTable(frame: PalTypeDbgStackFrame, scope: VarScope): SymbolTable {
        val container = buildContainer(frame, scope)
        return call("getSymbolTable") {
            val syts = tx.getSymbolTable(container)
            SymbolTable(container, syts?.toList().orEmpty())
        }
    }

    /** Fetch the value(s) for one variable. Group variables return their leaf values flattened. */
    fun getValue(container: IPalTypeDbgVarContainer, desc: IPalTypeDbgVarDesc): List<IPalTypeDbgVarValue> =
        call("getValue") { tx.getValue(container, desc)?.toList().orEmpty() }

    override fun close() {
        if (!closed.compareAndSet(false, true)) return
        if (!runtimeTerminated.get()) {
            try { call("debugExit") { tx.debugExit() } } catch (_: Throwable) {}
        }
        try { tx.close() } catch (_: Throwable) {}
        io.shutdownNow()
        if (runtimeTerminated.compareAndSet(false, true)) listener?.onTerminated(null)
    }

    // ---- helpers ----

    private fun dispatch(info: SuspendInfo) {
        if (info.terminated) {
            runtimeTerminated.set(true)
            listener?.onTerminated(info.errorText)
        } else {
            listener?.onSuspended(info)
        }
    }

    private fun handleSuspend(result: ISuspendResult, fallback: StopReason): SuspendInfo {
        val frames = result.stackFrames?.toList().orEmpty()
        val status = result.status
        val notify = result.notify
        val ex = result.exception
        val terminatedNow = status?.isTerminated == true ||
            notify?.notification == IPalTypeNotify.TERMINATE ||
            notify?.notification == IPalTypeNotify.SHUTDOWN ||
            notify?.notification == IPalTypeNotify.ABORT
        val reason = when {
            ex != null -> StopReason.EXCEPTION
            result.spy != null -> StopReason.BREAKPOINT
            else -> fallback
        }
        val errorText = ex?.let { it.shortText ?: it.message }
        return SuspendInfo(reason, frames, terminatedNow, errorText)
    }

    private fun createLineSpy(library: String, obj: String, line: Int): IPalTypeDbgSpy {
        val spy = PalTypeDbgSpyFactory.newInstance()
        spy.library = library.uppercase()
        spy.`object` = obj.uppercase()
        spy.line = line
        spy.flags = IPalTypeDbgSpy.SPY_BP
        spy.isActive = true
        return spy
    }

    private fun buildContainer(frame: PalTypeDbgStackFrame, scope: VarScope): IPalTypeDbgVarContainer {
        val flag = when (scope) {
            VarScope.LOCAL -> IPalTypeDbgVarContainer.VAR_LDA
            VarScope.GLOBAL -> IPalTypeDbgVarContainer.VAR_GDA
            VarScope.AIV -> IPalTypeDbgVarContainer.VAR_AIV
            VarScope.CONTEXT -> IPalTypeDbgVarContainer.VAR_CTX
            VarScope.SYSTEM -> IPalTypeDbgVarContainer.VAR_SYS
        }
        val container = PalTypeDbgVarContainerFactory.newInstance(flag)
        container.setStackLevel(frame.level)
        container.setNatType(frame.natType)
        when (scope) {
            VarScope.GLOBAL -> {
                container.setLibrary(frame.gdaLibrary ?: frame.library)
                container.setObject(frame.gdaObject ?: frame.`object`)
                container.setDatabaseId(frame.databaseIdGda)
                container.setFileNumber(frame.fileNbrGda)
            }
            else -> {
                container.setLibrary(frame.library)
                container.setObject(frame.`object`)
                container.setDatabaseId(frame.databaseId)
                container.setFileNumber(frame.fileNbr)
            }
        }
        return container
    }

    private fun <T> call(op: String, block: () -> T): T {
        if (closed.get()) throw NdsException("Debug session closed ($op)")
        if (runtimeTerminated.get() && op != "debugExit") throw NdsException("Debug session already terminated ($op)")
        val future = io.submit(Callable { block() })
        val deadline = System.nanoTime() + DEFAULT_TIMEOUT_NS
        while (true) {
            if (future.isDone) {
                return try { future.get() } catch (e: ExecutionException) {
                    val cause = e.cause ?: e
                    if (cause is PalResultException) throw NdsException(cause.shortText ?: cause.message ?: "Pal error", cause)
                    if (cause is NdsException) throw cause
                    throw NdsException(cause.message ?: cause.javaClass.simpleName, cause)
                }
            }
            if (System.nanoTime() > deadline) {
                future.cancel(true)
                throw NdsException("Debug call timed out: $op")
            }
            Thread.sleep(25)
        }
    }

    enum class VarScope { LOCAL, GLOBAL, AIV, CONTEXT, SYSTEM }

    data class SymbolTable(
        val container: IPalTypeDbgVarContainer,
        val entries: List<IPalTypeDbgSyt>,
    )

    private data class SpyKey(val library: String, val obj: String, val line: Int)

    companion object {
        private const val DEFAULT_TIMEOUT_NS = 60L * 1_000_000_000L

        private val CLIENT_ID = object : IPalClientIdentification {
            override fun getNdvClientVersion() = IPalClientIdentification.NATONE_VERSION
            override fun getNdvClientId() = IPalClientIdentification.PALCLIENTID_ONE
            override fun getWebIOVersion() = IPalClientIdentification.WEB_IO_VERSION
        }

        @Throws(NdsException::class)
        fun connect(
            host: String,
            port: Int,
            user: String,
            password: String,
            library: String,
            obj: String,
            parameter: String = "",
        ): NdsDebugSession {
            val io = Executors.newSingleThreadExecutor { r ->
                Thread(r, "nds-debug-io").apply { isDaemon = true }
            }
            val tx = PalTransactions(CLIENT_ID)
            val params = mapOf(
                ConnectKey.HOST to host,
                ConnectKey.PORT to port.toString(),
                ConnectKey.USERID to user.uppercase().take(8),
                ConnectKey.PASSWORD to password,
            )
            val future = io.submit(Callable<Unit> {
                tx.connect(params)
                tx.logon(library)
            })
            val deadline = System.nanoTime() + DEFAULT_TIMEOUT_NS
            while (true) {
                if (future.isDone) {
                    try { future.get() } catch (e: ExecutionException) {
                        io.shutdownNow()
                        val cause = e.cause ?: e
                        throw NdsException(cause.message ?: cause.javaClass.simpleName, cause)
                    }
                    break
                }
                if (System.nanoTime() > deadline) {
                    future.cancel(true)
                    io.shutdownNow()
                    throw NdsException("Debug connect timed out")
                }
                Thread.sleep(25)
            }
            return NdsDebugSession(tx, io, library, obj, parameter)
        }

        fun newVarDesc(syt: IPalTypeDbgSyt): IPalTypeDbgVarDesc =
            PalTypeDbgVarDescFactory.newInstance(syt, syt.indices)
    }
}
