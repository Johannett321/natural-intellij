package com.appweb.natural.intellij.compare

import com.appweb.natural.intellij.nds.NdsClient
import com.appweb.natural.intellij.nds.NdsObjectInfo
import com.appweb.natural.intellij.nds.NdsServer
import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffManager
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

/**
 * Opens IntelliJ's diff viewer with the local file on the left (editable) and the server-side
 * source of the corresponding Natural object on the right (read-only). The server source is
 * fetched in the background using the same connection helpers used by the NDS tool window.
 */
object NaturalCompareService {

    fun showDiff(
        project: Project,
        server: NdsServer,
        libraryName: String,
        info: NdsObjectInfo,
        localFile: VirtualFile,
    ) {
        val title = "Downloading ${libraryName}.${info.name}"
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, title, true) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                try {
                    val password = NdsServerSettings.getInstance().getPassword(server)
                    val source = NdsClient.connect(
                        server.host, server.port, server.user, password, server.logonLibrary
                    ).use { it.downloadSource(libraryName, info) }
                    onEdt { openDiff(project, server, libraryName, info, localFile, source) }
                } catch (e: Throwable) {
                    onEdt {
                        Messages.showErrorDialog(
                            project,
                            "Could not download ${libraryName}.${info.name}:\n${e.message ?: e.javaClass.simpleName}",
                            "Natural",
                        )
                    }
                }
            }
        })
    }

    private fun openDiff(
        project: Project,
        server: NdsServer,
        libraryName: String,
        info: NdsObjectInfo,
        localFile: VirtualFile,
        serverSource: String,
    ) {
        val factory = DiffContentFactory.getInstance()
        val fileType: FileType = info.extension
            ?.let { FileTypeManager.getInstance().getFileTypeByExtension(it) }
            ?: PlainTextFileType.INSTANCE

        val localContent = factory.create(project, localFile)
        val serverContent = factory.create(project, serverSource, fileType)

        val request = SimpleDiffRequest(
            "${libraryName}.${info.name} — Local vs Server (${server.displayName})",
            localContent,
            serverContent,
            "Local: ${localFile.presentableUrl}",
            "Server: ${server.displayName} / ${libraryName}.${info.name}.${info.extension ?: ""}",
        )
        DiffManager.getInstance().showDiff(project, request)
    }

    private fun onEdt(body: () -> Unit) {
        ApplicationManager.getApplication().invokeLater(body)
    }
}
