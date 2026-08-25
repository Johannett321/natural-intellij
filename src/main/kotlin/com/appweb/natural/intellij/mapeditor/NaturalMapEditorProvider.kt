package com.appweb.natural.intellij.mapeditor

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.VirtualFile

class NaturalMapEditorProvider : FileEditorProvider, DumbAware {

    override fun accept(project: Project, file: VirtualFile): Boolean =
        file.extension?.uppercase() == "NSM"

    override fun createEditor(project: Project, file: VirtualFile): FileEditor =
        NaturalMapEditor(project, file)

    override fun disposeEditor(editor: FileEditor) = Disposer.dispose(editor)

    override fun getEditorTypeId(): String = "natural-map-visual-editor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR
}
