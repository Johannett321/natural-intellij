package com.appweb.natural.intellij.debug

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.xdebugger.XDebuggerUtil
import com.intellij.xdebugger.XSourcePosition
import java.util.concurrent.ConcurrentHashMap

/**
 * Resolves a Natural (library, object) coordinate from the debug server to a [VirtualFile] in the
 * local project.
 *
 * Convention: under the project root, each library is a directory with the library name (case
 * sensitive on Linux, but we match case-insensitively), and each object is a file named
 * `OBJECTNAME.NS*`.
 */
class NaturalSourceLocator(private val project: Project) {

    private val cache = ConcurrentHashMap<Key, VirtualFile>()

    /** Look up the local file for ([library], [obj]). Returns null if not found in this project. */
    fun find(library: String?, obj: String?): VirtualFile? {
        if (library.isNullOrBlank() || obj.isNullOrBlank()) return null
        val key = Key(library.uppercase(), obj.uppercase())
        cache[key]?.takeIf { it.isValid }?.let { return it }

        val basePath = project.basePath ?: return null
        val baseDir = VfsUtil.findFile(java.nio.file.Paths.get(basePath), true) ?: return null

        val libDir = baseDir.children.firstOrNull { it.isDirectory && it.name.equals(library, ignoreCase = true) }
            ?: return null

        val match = libDir.children.firstOrNull { f ->
            if (f.isDirectory) return@firstOrNull false
            val ext = f.extension?.uppercase() ?: return@firstOrNull false
            ext in NaturalLineBreakpointType.EXECUTABLE_EXTENSIONS &&
                f.nameWithoutExtension.equals(obj, ignoreCase = true)
        }

        return match?.also { cache[key] = it }
    }

    fun sourcePosition(library: String?, obj: String?, lineOneBased: Int): XSourcePosition? {
        val file = find(library, obj) ?: return null
        val zero = (lineOneBased - 1).coerceAtLeast(0)
        return XDebuggerUtil.getInstance().createPosition(file, zero)
    }

    /**
     * Inverse direction: given a VirtualFile, derive (library, object) using the project layout
     * convention. Returns null if the file isn't a Natural source under a library directory.
     */
    fun coordinates(file: VirtualFile): Coord? {
        val basePath = project.basePath ?: return null
        val rel = VfsUtil.getRelativePath(file, VfsUtil.findFile(java.nio.file.Paths.get(basePath), true) ?: return null)
            ?: return null
        val parts = rel.split('/')
        if (parts.size < 2) return null
        val library = parts.first()
        val obj = file.nameWithoutExtension
        val ext = file.extension?.uppercase() ?: return null
        if (ext !in NaturalLineBreakpointType.EXECUTABLE_EXTENSIONS) return null
        return Coord(library.uppercase(), obj.uppercase(), ext)
    }

    data class Coord(val library: String, val obj: String, val ext: String)

    private data class Key(val library: String, val obj: String)
}
