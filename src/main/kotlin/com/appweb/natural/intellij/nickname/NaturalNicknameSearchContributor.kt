package com.appweb.natural.intellij.nickname

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Processor
import com.intellij.util.ui.UIUtil
import java.awt.Component
import javax.swing.*

class NaturalNicknameSearchContributor(private val project: Project?) : SearchEverywhereContributor<VirtualFile> {

    private val gson = GsonBuilder().create()

    override fun getSearchProviderId() = "NaturalNickname.SearchEverywhereContributor"
    override fun getGroupName() = "Natural Files"
    override fun getSortWeight() = 200
    override fun showInFindResults() = false
    override fun isShownInSeparateTab() = false

    override fun fetchElements(
        pattern: String,
        progressIndicator: ProgressIndicator,
        consumer: Processor<in VirtualFile>
    ) {
        val proj = project ?: return
        if (pattern.isBlank()) return
        val lowerPattern = pattern.lowercase()

        ReadAction.run<Exception> {
            val nicknameMatched = linkedSetOf<VirtualFile>()
            val nameMatched = linkedSetOf<VirtualFile>()

            val basePath = proj.basePath ?: return@run
            val projectRoot = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return@run

            VfsUtil.iterateChildrenRecursively(projectRoot, null) { vFile ->
                if (progressIndicator.isCanceled) return@iterateChildrenRecursively false
                if (!vFile.isDirectory) {
                    val ext = vFile.extension?.uppercase()
                    if (ext != null && ext in NATURAL_EXTENSIONS) {
                        if (vFile.nameWithoutExtension.lowercase().contains(lowerPattern)) {
                            nameMatched.add(vFile)
                        }
                    }
                    if (vFile.name == NaturalNicknameService.NICKNAME_FILE) {
                        collectNicknameMatches(vFile, lowerPattern, nicknameMatched)
                    }
                }
                true
            }

            for (file in nicknameMatched) {
                if (progressIndicator.isCanceled) break
                consumer.process(file)
            }
            for (file in nameMatched) {
                if (progressIndicator.isCanceled) break
                if (!nicknameMatched.contains(file)) consumer.process(file)
            }
        }
    }

    companion object {
        private val NATURAL_EXTENSIONS = setOf("NSP", "NSN", "NSC", "NSS", "NST", "GDA", "DDM", "MAP", "LDA", "PDA", "NSA")
    }

    private fun collectNicknameMatches(
        nicknameFile: VirtualFile,
        lowerPattern: String,
        result: MutableSet<VirtualFile>
    ) {
        val dir = nicknameFile.parent ?: return
        try {
            val content = String(nicknameFile.contentsToByteArray(), Charsets.UTF_8)
            val type = object : TypeToken<Map<String, String>>() {}.type
            val nicknames: Map<String, String> = gson.fromJson(content, type) ?: return
            for ((filename, nickname) in nicknames) {
                if (nickname.lowercase().contains(lowerPattern)) {
                    val naturalFile = dir.findChild(filename) ?: dir.findChild(filename.lowercase())
                    if (naturalFile != null) result.add(naturalFile)
                }
            }
        } catch (_: Exception) { }
    }

    override fun processSelectedItem(selected: VirtualFile, modifiers: Int, searchText: String): Boolean {
        val proj = project ?: return false
        OpenFileDescriptor(proj, selected).navigate(true)
        return true
    }

    override fun getElementsRenderer() = object : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean
        ): Component {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            val file = value as? VirtualFile ?: return this
            val nickname = NaturalNicknameService.getNickname(file)
            if (nickname != null) {
                val grayColor = UIUtil.getContextHelpForeground()
                val grayHex = "#%02x%02x%02x".format(grayColor.red, grayColor.green, grayColor.blue)
                text = "<html><b>$nickname</b>&nbsp;<font color='$grayHex'>${file.name}</font></html>"
            } else {
                text = file.name
            }
            icon = file.fileType.icon
            border = BorderFactory.createEmptyBorder(2, 8, 2, 8)
            return this
        }
    }

    override fun getDataForItem(element: VirtualFile, dataId: String): Any? = null
}

class NaturalNicknameSearchContributorFactory : SearchEverywhereContributorFactory<VirtualFile> {
    override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<VirtualFile> {
        return NaturalNicknameSearchContributor(initEvent.project)
    }
}
