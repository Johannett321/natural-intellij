package com.appweb.natural.intellij.language

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

abstract class NaturalFileType(name: String, description: String, extension: String, icon: Icon) : LanguageFileType(NaturalLanguage.INSTANCE) {
    private val _name = name
    private val _description = description
    private val _extension = extension
    private val _icon = icon

    override fun getName(): String = _name
    override fun getDisplayName(): String = _name
    override fun getDescription(): String = _description
    override fun getDefaultExtension(): String = _extension
    override fun getIcon(): Icon? = _icon
}