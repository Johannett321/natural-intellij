package com.appweb.natural.intellij.toolwindow

import com.appweb.natural.intellij.NaturalIcons
import com.appweb.natural.intellij.compare.NaturalCompareService
import com.appweb.natural.intellij.nds.NdsClient
import com.appweb.natural.intellij.nds.NdsObjectInfo
import com.appweb.natural.intellij.nds.NdsServer
import com.appweb.natural.intellij.nds.NdsServerSettings
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.softwareag.naturalone.natural.pal.external.ObjectType
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.testFramework.LightVirtualFile
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import javax.swing.Icon
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.JTree
import javax.swing.SwingUtilities
import javax.swing.event.TreeExpansionEvent
import javax.swing.event.TreeExpansionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath
import javax.swing.tree.TreeSelectionModel

class NdsToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = NdsServerPanel(project)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

/**
 * Tool window panel: a tree of  Servers → Libraries → Objects, lazily populated from NDV.
 * Double-click an object → background-download its source and open it in the editor.
 */
private class NdsServerPanel(private val project: Project) : JPanel(BorderLayout()) {

    private val rootNode = DefaultMutableTreeNode("Natural Servers")
    private val model = DefaultTreeModel(rootNode)
    private val tree = JTree(model).apply {
        isRootVisible = false
        showsRootHandles = true
        selectionModel.selectionMode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION
        cellRenderer = NdsTreeCellRenderer()
    }

    init {
        // Toolbar
        val refreshAction = object : AnAction("Refresh", "Reload server list", AllIcons.Actions.Refresh) {
            override fun actionPerformed(e: AnActionEvent) { reloadServers() }
        }
        val configureAction = object : AnAction("Configure Servers…", "Add / edit / remove servers", AllIcons.General.Settings) {
            override fun actionPerformed(e: AnActionEvent) {
                ShowSettingsUtil.getInstance()
                    .showSettingsDialog(project, "Natural Servers")
                reloadServers()
            }
        }
        val group = DefaultActionGroup(refreshAction, configureAction)
        val toolbar = ActionManager.getInstance().createActionToolbar("NaturalServers", group, true)
        toolbar.targetComponent = tree
        add(toolbar.component, BorderLayout.NORTH)
        add(JBScrollPane(tree), BorderLayout.CENTER)

        // Lazy expand
        tree.addTreeExpansionListener(object : TreeExpansionListener {
            override fun treeExpanded(event: TreeExpansionEvent) {
                val node = event.path.lastPathComponent as? DefaultMutableTreeNode ?: return
                val userObject = node.userObject
                if (userObject is ServerEntry && !userObject.loaded) loadLibraries(node, userObject)
                else if (userObject is LibraryEntry && !userObject.loaded) loadObjects(node, userObject)
            }
            override fun treeCollapsed(event: TreeExpansionEvent) {}
        })

        // Double-click opens source; right-click shows context menu
        tree.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount != 2 || !SwingUtilities.isLeftMouseButton(e)) return
                val entry = nodeAt(e)?.userObject as? ObjectEntry ?: return
                openObject(entry)
            }
            override fun mousePressed(e: MouseEvent)  { maybePopup(e) }
            override fun mouseReleased(e: MouseEvent) { maybePopup(e) }
        })

        reloadServers()
    }

    private fun reloadServers() {
        rootNode.removeAllChildren()
        val servers = NdsServerSettings.getInstance().servers
        if (servers.isEmpty()) {
            rootNode.add(DefaultMutableTreeNode(Hint("No servers configured. Use the gear icon → Configure Servers…")))
        } else {
            servers.forEach { srv ->
                val node = DefaultMutableTreeNode(ServerEntry(srv))
                node.add(DefaultMutableTreeNode(Hint("Expand to load libraries…")))
                rootNode.add(node)
            }
        }
        model.reload()
    }

    private fun loadLibraries(node: DefaultMutableTreeNode, entry: ServerEntry) {
        node.removeAllChildren()
        node.add(DefaultMutableTreeNode(Hint("Loading…")))
        model.reload(node)
        runBackground("Loading libraries from ${entry.server.displayName}") {
            try {
                openClient(entry.server).use { client ->
                    val libs = client.listLibraries()
                    onEdt {
                        node.removeAllChildren()
                        if (libs.isEmpty()) {
                            node.add(DefaultMutableTreeNode(Hint("No libraries")))
                        } else {
                            libs.forEach { name ->
                                val lib = DefaultMutableTreeNode(LibraryEntry(entry.server, name))
                                lib.add(DefaultMutableTreeNode(Hint("Expand to load objects…")))
                                node.add(lib)
                            }
                        }
                        entry.loaded = true
                        model.reload(node)
                    }
                }
            } catch (e: Throwable) {
                showError(node, "Failed to load libraries", e)
            }
        }
    }

    private fun loadObjects(node: DefaultMutableTreeNode, entry: LibraryEntry) {
        node.removeAllChildren()
        node.add(DefaultMutableTreeNode(Hint("Loading…")))
        model.reload(node)
        runBackground("Loading objects in ${entry.libraryName}") {
            try {
                openClient(entry.server).use { client ->
                    val objs = client.listObjects(entry.libraryName)
                    onEdt {
                        node.removeAllChildren()
                        if (objs.isEmpty()) {
                            node.add(DefaultMutableTreeNode(Hint("(empty)")))
                        } else {
                            objs.forEach { o ->
                                node.add(DefaultMutableTreeNode(ObjectEntry(entry.server, entry.libraryName, o)))
                            }
                        }
                        entry.loaded = true
                        model.reload(node)
                    }
                }
            } catch (e: Throwable) {
                showError(node, "Failed to load objects in ${entry.libraryName}", e)
            }
        }
    }

    private fun nodeAt(e: MouseEvent): DefaultMutableTreeNode? {
        // Use the closest row at this y-coordinate so clicks anywhere along the
        // row — not just on the rendered text — count. Still require the click
        // to be within the row's vertical bounds, so we don't grab a node when
        // clicking in empty space below the last row.
        val row = tree.getClosestRowForLocation(e.x, e.y)
        if (row < 0) return null
        val bounds = tree.getRowBounds(row) ?: return null
        if (e.y < bounds.y || e.y >= bounds.y + bounds.height) return null
        val path = tree.getPathForRow(row) ?: return null
        return path.lastPathComponent as? DefaultMutableTreeNode
    }

    private fun maybePopup(e: MouseEvent) {
        if (!e.isPopupTrigger) return
        val clicked = nodeAt(e) ?: return

        // If the clicked node is part of the current selection, treat all selected nodes as targets.
        // Otherwise narrow to the clicked node only and replace the selection so the user sees what
        // the popup will act on.
        val currentSelection = (tree.selectionPaths ?: emptyArray())
            .mapNotNull { it.lastPathComponent as? DefaultMutableTreeNode }
        val targets = if (clicked in currentSelection && currentSelection.size > 1) currentSelection
                      else { tree.selectionPath = TreePath(clicked.path); listOf(clicked) }

        val menu = buildPopupMenu(targets) ?: return
        menu.show(tree, e.x, e.y)
    }

    private fun buildPopupMenu(targets: List<DefaultMutableTreeNode>): JPopupMenu? {
        val menu = JPopupMenu()

        val objectEntries = targets.mapNotNull { it.userObject as? ObjectEntry }
        val libraryEntries = targets.mapNotNull { it.userObject as? LibraryEntry }

        when {
            // Pure single-object: Open + Download + Compare
            objectEntries.size == 1 && libraryEntries.isEmpty() -> {
                val entry = objectEntries.first()
                menu.add("Open").addActionListener { openObject(entry) }
                val downloadItem = menu.add("Download to project")
                downloadItem.isEnabled = entry.info.hasSource
                downloadItem.addActionListener { downloadObjectsToProject(listOf(entry)) }
                val compareItem = menu.add("Compare with local file…")
                compareItem.isEnabled = entry.info.hasSource
                compareItem.addActionListener { compareObjectWithLocal(entry) }
            }
            // Multiple objects: Download N
            objectEntries.size > 1 && libraryEntries.isEmpty() -> {
                val n = objectEntries.size
                menu.add("Download $n objects to project")
                    .addActionListener { downloadObjectsToProject(objectEntries) }
            }
            // Single library: Download library
            libraryEntries.size == 1 && objectEntries.isEmpty() -> {
                val lib = libraryEntries.first()
                menu.add("Download library '${lib.libraryName}' to project")
                    .addActionListener { downloadLibrariesToProject(libraryEntries) }
            }
            // Multiple libraries
            libraryEntries.size > 1 && objectEntries.isEmpty() -> {
                menu.add("Download ${libraryEntries.size} libraries to project")
                    .addActionListener { downloadLibrariesToProject(libraryEntries) }
            }
            else -> return null
        }
        return menu
    }

    private fun openObject(entry: ObjectEntry) {
        if (!entry.info.hasSource) {
            Messages.showInfoMessage(project,
                "${entry.info.typeLabel} '${entry.info.name}' is not a downloadable source object.",
                "Natural")
            return
        }
        runBackground("Downloading ${entry.libraryName}.${entry.info.name}") {
            try {
                val source = openClient(entry.server).use { it.downloadSource(entry.libraryName, entry.info) }
                onEdt {
                    val fileName = "${entry.info.name}.${entry.info.extension}"
                    val vf = LightVirtualFile(fileName, source)
                    vf.isWritable = false
                    FileEditorManager.getInstance(project).openFile(vf, true)
                }
            } catch (e: Throwable) {
                onEdt {
                    Messages.showErrorDialog(project,
                        "Could not download ${entry.libraryName}.${entry.info.name}:\n${e.message}",
                        "Natural")
                }
            }
        }
    }

    private fun compareObjectWithLocal(entry: ObjectEntry) {
        if (!entry.info.hasSource) {
            Messages.showInfoMessage(project,
                "${entry.info.typeLabel} '${entry.info.name}' is not a downloadable source object.",
                "Natural")
            return
        }
        val extension = entry.info.extension ?: return
        val fileName = "${entry.info.name}.${extension}"

        // Look up project files matching {OBJECT}.{EXT}. FilenameIndex is case-sensitive, so
        // also check the lowercase variant — local copies on case-sensitive filesystems may
        // disagree with the uppercase server convention.
        val scope = GlobalSearchScope.projectScope(project)
        val candidates = (FilenameIndex.getVirtualFilesByName(fileName, scope) +
                          FilenameIndex.getVirtualFilesByName(fileName.lowercase(), scope))
            .filter { it.isValid && !it.isDirectory }
            .distinctBy { it.path }

        // Prefer candidates that live under the library folder — either directly (LIB/X.NSP) or
        // in a type / SRC sub-folder (LIB/Programs/X.NSP), the layouts downloads write to.
        val preferred = candidates.filter {
            val parent = it.parent
            parent?.name.equals(entry.libraryName, ignoreCase = true) ||
                parent?.parent?.name.equals(entry.libraryName, ignoreCase = true)
        }
        val ranked = if (preferred.isNotEmpty()) preferred else candidates

        when (ranked.size) {
            0 -> chooseLocalFileManually(entry, extension)
            1 -> NaturalCompareService.showDiff(project, entry.server, entry.libraryName, entry.info, ranked.first())
            else -> chooseFromCandidates(entry, ranked)
        }
    }

    private fun chooseLocalFileManually(entry: ObjectEntry, extension: String) {
        val message = "No local file named '${entry.info.name}.${extension}' was found in this project." +
                      "\nPick a file to compare against, or cancel."
        val choice = Messages.showYesNoDialog(project, message, "Compare with Local", "Choose File…", "Cancel", null)
        if (choice != Messages.YES) return
        val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
            .withTitle("Choose Local File to Compare Against")
        val picked = FileChooser.chooseFile(descriptor, project, null) ?: return
        NaturalCompareService.showDiff(project, entry.server, entry.libraryName, entry.info, picked)
    }

    private fun chooseFromCandidates(entry: ObjectEntry, candidates: List<com.intellij.openapi.vfs.VirtualFile>) {
        val basePath = project.basePath
        val labels = candidates.map { vf ->
            val p = vf.path
            if (basePath != null && p.startsWith(basePath)) p.removePrefix(basePath).removePrefix("/")
            else p
        }
        JBPopupFactory.getInstance()
            .createPopupChooserBuilder(labels)
            .setTitle("Compare ${entry.libraryName}.${entry.info.name} with…")
            .setItemChosenCallback { chosen ->
                val idx = labels.indexOf(chosen)
                if (idx >= 0) {
                    NaturalCompareService.showDiff(
                        project, entry.server, entry.libraryName, entry.info, candidates[idx]
                    )
                }
            }
            .createPopup()
            .showInFocusCenter()
    }

    private fun projectRoot(): Path? {
        val base = project.basePath
        if (base == null) {
            Messages.showErrorDialog(project,
                "Cannot download: this project has no base directory.", "Natural")
            return null
        }
        return Path.of(base)
    }

    /**
     * Where a downloaded object belongs on disk. NaturalONE projects keep sources in per-type
     * folders (TRMUH/Programs/T657B2-P.NSP); some checkouts use a flat SRC folder instead, and
     * older downloads landed straight in the library folder. If a copy already exists in any of
     * those places we overwrite that one, so a download replaces the file the user actually
     * edits instead of creating a duplicate next to it.
     */
    private fun targetFileFor(libDir: Path, info: NdsObjectInfo): Path {
        val fileName = "${info.name}.${info.extension}"
        val typeDir = info.typeFolder?.let { libDir.resolve(it) }
        val srcDir = libDir.resolve("SRC")

        val existing = listOfNotNull(typeDir, srcDir, libDir)
            .map { it.resolve(fileName) }
            .firstOrNull { Files.isRegularFile(it) }
        if (existing != null) return existing

        val dir = when {
            Files.isDirectory(srcDir) -> srcDir   // project follows the flat SRC convention
            typeDir != null           -> typeDir  // NaturalONE per-type folder
            else                      -> libDir   // unknown object type
        }
        return dir.resolve(fileName)
    }

    private fun downloadObjectsToProject(entries: List<ObjectEntry>) {
        val downloadable = entries.filter { it.info.hasSource }
        if (downloadable.isEmpty()) {
            Messages.showInfoMessage(project,
                "None of the selected objects have downloadable source.", "Natural")
            return
        }
        val targetRoot = projectRoot() ?: return
        val title = if (downloadable.size == 1) "Downloading ${downloadable.first().info.name}"
                    else "Downloading ${downloadable.size} objects"

        runBackground(title) {
            var dl = 0
            var fail = 0
            val touchedDirs = mutableSetOf<Path>()
            // Group by (server, library) so we use one NDV connection per group
            val groups = downloadable.groupBy { it.server.id to it.libraryName }
            for ((_, group) in groups) {
                val server = group.first().server
                val libName = group.first().libraryName
                try {
                    openClient(server).use { client ->
                        val libDir = targetRoot.resolve(libName)
                        touchedDirs.add(libDir)
                        for (entry in group) {
                            try {
                                val source = client.downloadSource(libName, entry.info)
                                val outFile = targetFileFor(libDir, entry.info)
                                Files.createDirectories(outFile.parent)
                                Files.writeString(outFile, source + "\n", StandardCharsets.UTF_8)
                                dl++
                            } catch (e: Throwable) {
                                fail++
                            }
                        }
                    }
                } catch (e: Throwable) {
                    fail += group.size
                }
            }
            refreshDownloaded(touchedDirs)
            onEdt {
                val msg = if (fail == 0) "Saved $dl object(s) to $targetRoot"
                          else "Saved $dl object(s), $fail failed. Folder: $targetRoot"
                Messages.showInfoMessage(project, msg, "Natural")
            }
        }
    }

    private fun downloadLibrariesToProject(libraries: List<LibraryEntry>) {
        if (libraries.isEmpty()) return
        val targetRoot = projectRoot() ?: return
        val title = if (libraries.size == 1) "Downloading library ${libraries.first().libraryName}"
                    else "Downloading ${libraries.size} libraries"
        runBackground(title) {
            var totalDl = 0
            var totalFail = 0
            val touchedDirs = mutableSetOf<Path>()
            for (lib in libraries) {
                try {
                    openClient(lib.server).use { client ->
                        val libDir = targetRoot.resolve(lib.libraryName)
                        val objs = client.listObjects(lib.libraryName)
                        val downloadable = objs.filter { it.hasSource }
                        if (downloadable.isNotEmpty()) touchedDirs.add(libDir)
                        for (obj in downloadable) {
                            try {
                                val source = client.downloadSource(lib.libraryName, obj)
                                val outFile = targetFileFor(libDir, obj)
                                Files.createDirectories(outFile.parent)
                                Files.writeString(outFile, source + "\n", StandardCharsets.UTF_8)
                                totalDl++
                            } catch (e: Throwable) {
                                totalFail++
                            }
                        }
                    }
                } catch (e: Throwable) {
                    totalFail++ // count the whole library as one failure
                }
            }
            refreshDownloaded(touchedDirs)
            onEdt {
                val msg = if (totalFail == 0) "Saved $totalDl object(s) to $targetRoot"
                          else "Saved $totalDl object(s), $totalFail failed. Folder: $targetRoot"
                Messages.showInfoMessage(project, msg, "Natural")
            }
        }
    }

    /**
     * Make the VFS pick up newly-written files so the user doesn't have to
     * trigger "Reload from disk". refreshAndFindFileByPath only refreshes the
     * named entry; we follow up with a recursive refresh of each library dir
     * so its children (the freshly-written sources) are scanned in.
     */
    private fun refreshDownloaded(dirs: Set<Path>) {
        if (dirs.isEmpty()) return
        onEdt {
            val lfs = LocalFileSystem.getInstance()
            for (dir in dirs) {
                lfs.refreshAndFindFileByPath(dir.toString())?.refresh(true, true)
            }
        }
    }

    private fun openClient(server: NdsServer): NdsClient {
        val password = NdsServerSettings.getInstance().getPassword(server)
        return NdsClient.connect(server.host, server.port, server.user, password, server.logonLibrary)
    }

    private fun runBackground(title: String, body: () -> Unit) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, title, true) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                body()
            }
        })
    }

    private fun onEdt(body: () -> Unit) {
        if (SwingUtilities.isEventDispatchThread()) body()
        else ApplicationManager.getApplication().invokeLater(body)
    }

    private fun showError(node: DefaultMutableTreeNode, label: String, e: Throwable) {
        onEdt {
            node.removeAllChildren()
            node.add(DefaultMutableTreeNode(Hint("$label: ${e.message ?: e.javaClass.simpleName}")))
            model.reload(node)
        }
    }

    /* ---- Tree entry types ---- */

    private class ServerEntry(val server: NdsServer) {
        var loaded = false
        override fun toString() = server.displayName
    }
    private class LibraryEntry(val server: NdsServer, val libraryName: String) {
        var loaded = false
        override fun toString() = libraryName
    }
    private class ObjectEntry(val server: NdsServer, val libraryName: String, val info: NdsObjectInfo) {
        override fun toString() = info.name
    }
    private class Hint(private val text: String) { override fun toString() = text }

    private class NdsTreeCellRenderer : DefaultTreeCellRenderer() {
        private val serverIcon: Icon = IconLoader.getIcon("/icons/naturalServers.svg", NdsTreeCellRenderer::class.java)

        override fun getTreeCellRendererComponent(
            tree: JTree, value: Any?, sel: Boolean, expanded: Boolean,
            leaf: Boolean, row: Int, hasFocus: Boolean,
        ): java.awt.Component {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus)
            val node = value as? DefaultMutableTreeNode
            icon = when (val u = node?.userObject) {
                is ServerEntry  -> serverIcon
                is LibraryEntry -> AllIcons.Nodes.Package
                is ObjectEntry  -> iconFor(u.info)
                is Hint         -> AllIcons.General.Information
                else            -> null
            }
            return this
        }

        private fun iconFor(info: NdsObjectInfo): Icon = when (info.type) {
            ObjectType.PROGRAM     -> NaturalIcons.PROGRAM
            ObjectType.SUBPROGRAM  -> NaturalIcons.SUBPROGRAM
            ObjectType.SUBROUTINE  -> NaturalIcons.SUBROUTINE
            ObjectType.COPYCODE    -> NaturalIcons.COPYCODE
            ObjectType.MAP         -> NaturalIcons.MAP
            ObjectType.LDA         -> NaturalIcons.LDA
            ObjectType.PDA         -> NaturalIcons.PDA
            ObjectType.GDA         -> NaturalIcons.GDA
            ObjectType.DDM         -> NaturalIcons.DDM
            ObjectType.TEXT        -> NaturalIcons.TEXT
            else                   -> AllIcons.FileTypes.Any_type
        }
    }
}
