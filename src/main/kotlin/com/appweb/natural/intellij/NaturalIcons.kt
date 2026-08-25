package com.appweb.natural.intellij

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Icon set for Natural objects.
 *
 * Every icon is an original 16x16 SVG shipped with this plugin, with a `_dark` variant picked up
 * automatically by [IconLoader]. Colour encodes the object family: blue for executable code,
 * purple for copycode, green for screens and data definitions, amber for data areas,
 * grey for text and infrastructure.
 */
object NaturalIcons {
    private fun load(name: String): Icon =
        IconLoader.getIcon("/icons/$name.svg", NaturalIcons::class.java)

    /** The Natural language mark, used for the plugin and unclassified sources. */
    @JvmField val NATURAL: Icon = load("natural")

    // Executable code
    @JvmField val PROGRAM: Icon = load("naturalProgram")
    @JvmField val SUBPROGRAM: Icon = load("naturalSubprogram")
    @JvmField val SUBROUTINE: Icon = load("naturalSubroutine")
    @JvmField val COPYCODE: Icon = load("naturalCopycode")

    // Screens and data definitions
    @JvmField val MAP: Icon = load("naturalMap")
    @JvmField val DDM: Icon = load("naturalDdm")

    // Data areas
    @JvmField val GDA: Icon = load("naturalGda")
    @JvmField val LDA: Icon = load("naturalLda")
    @JvmField val PDA: Icon = load("naturalPda")

    // Text and infrastructure
    @JvmField val TEXT: Icon = load("naturalText")
    @JvmField val CONFIG: Icon = load("naturalConfig")
    @JvmField val SERVER: Icon = load("naturalServers")
    @JvmField val LIBRARY: Icon = load("naturalLibrary")
    @JvmField val STOW: Icon = load("naturalStow")
}
