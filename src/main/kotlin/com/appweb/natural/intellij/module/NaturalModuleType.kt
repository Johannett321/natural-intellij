package com.appweb.natural.intellij.module

import com.appweb.natural.intellij.NaturalIcons
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import javax.swing.Icon

class NaturalModuleType : ModuleType<NaturalModuleBuilder>(ID) {

    override fun createModuleBuilder(): NaturalModuleBuilder = NaturalModuleBuilder()

    override fun getName(): String = "Natural"

    override fun getDescription(): String =
        "A Natural / NaturalONE module containing programs, subprograms, data areas, copycodes, maps, and text objects. " +
            "Configure a Natural Development Server in the Natural Servers tool window to stow and check against it."

    override fun getNodeIcon(isOpened: Boolean): Icon = NaturalIcons.PROGRAM

    companion object {
        const val ID: String = "NATURAL_MODULE_TYPE"

        @JvmStatic
        fun getInstance(): NaturalModuleType =
            ModuleTypeManager.getInstance().findByID(ID) as NaturalModuleType
    }
}
