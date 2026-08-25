package com.appweb.natural.intellij.toolwindow

import com.appweb.natural.intellij.NaturalIcons
import com.appweb.natural.intellij.language.NaturalFileType
import com.appweb.natural.intellij.language.filetypes.NaturalCopyCodeFileType
import com.appweb.natural.intellij.language.filetypes.NaturalGDAFileType
import com.appweb.natural.intellij.language.filetypes.NaturalLDAFileType
import com.appweb.natural.intellij.language.filetypes.NaturalProgramFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubProgramFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubRoutineFileType
import com.appweb.natural.intellij.language.filetypes.NaturalSubprogramParameterFileType
import com.appweb.natural.intellij.psi.NaturalCallnatStatement
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalDefineDataPhase
import com.appweb.natural.intellij.psi.NaturalIncludeStatement
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Icon
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.SwingUtilities
import javax.swing.event.TreeExpansionEvent
import javax.swing.event.TreeWillExpandListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel

// ─── Data model ───────────────────────────────────────────────────────────────

enum class DepKind(val label: String, val icon: Icon) {
    ROOT("", NaturalIcons.PROGRAM),
    CALLNAT("CALLNAT", NaturalIcons.SUBPROGRAM),
    INCLUDE("INCLUDE", NaturalIcons.COPYCODE),
    LOCAL_USING("LOCAL USING", NaturalIcons.LDA),
    GLOBAL_USING("GLOBAL USING", NaturalIcons.GDA),
    PARAMETER_USING("PARAMETER USING", NaturalIcons.PDA)
}

data class DepNode(
    val name: String,
    val vFile: VirtualFile?,
    val kind: DepKind,
    val isCycle: Boolean = false,
    val ancestorPaths: Set<String> = emptySet()
)

private object LoadingNode
private object EmptyNode

// ─── Tool window factory ──────────────────────────────────────────────────────

class NaturalDependencyToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = NaturalDependencyPanel(project)
        Disposer.register(toolWindow.disposable, panel)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

// ─── Panel ────────────────────────────────────────────────────────────────────

class NaturalDependencyPanel(private val project: Project) :
    JPanel(BorderLayout()), FileEditorManagerListener, Disposable {

    private val rootNode = DefaultMutableTreeNode(EmptyNode)
    private val model = DefaultTreeModel(rootNode)
    private val tree = JTree(model).apply {
        isRootVisible = true
        showsRootHandles = true
        cellRenderer = DepCellRenderer()
    }

    init {
        add(JBScrollPane(tree), BorderLayout.CENTER)

        tree.addTreeWillExpandListener(object : TreeWillExpandListener {
            override fun treeWillExpand(event: TreeExpansionEvent) {
                val node = event.path.lastPathComponent as? DefaultMutableTreeNode ?: return
                maybeLoad(node)
            }
            override fun treeWillCollapse(event: TreeExpansionEvent) {}
        })

        tree.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount < 2) return
                val path = tree.getPathForLocation(e.x, e.y) ?: return
                val dep = (path.lastPathComponent as? DefaultMutableTreeNode)
                    ?.userObject as? DepNode ?: return
                dep.vFile?.let { OpenFileDescriptor(project, it).navigate(true) }
            }
        })

        project.messageBus.connect(this)
            .subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this)
        showFile(FileEditorManager.getInstance(project).selectedFiles.firstOrNull())
    }

    override fun selectionChanged(event: FileEditorManagerEvent) = showFile(event.newFile)

    private fun showFile(vFile: VirtualFile?) {
        rootNode.removeAllChildren()
        if (vFile == null || vFile.fileType !is NaturalFileType) {
            rootNode.userObject = EmptyNode
            model.reload()
            return
        }
        rootNode.userObject = DepNode(
            name = vFile.name,
            vFile = vFile,
            kind = rootKind(vFile),
            ancestorPaths = setOf(vFile.path)
        )
        rootNode.add(DefaultMutableTreeNode(LoadingNode))
        model.reload()
        scheduleLoad(rootNode)
        tree.expandRow(0)
    }

    private fun maybeLoad(node: DefaultMutableTreeNode) {
        if (node.childCount != 1) return
        val placeholder = node.getChildAt(0) as? DefaultMutableTreeNode ?: return
        if (placeholder.userObject !== LoadingNode) return
        scheduleLoad(node)
    }

    private fun scheduleLoad(node: DefaultMutableTreeNode) {
        val nodeData = node.userObject as? DepNode ?: return
        val vFile = nodeData.vFile ?: return
        if (nodeData.isCycle) return

        ApplicationManager.getApplication().executeOnPooledThread {
            val deps = ReadAction.compute<List<DepNode>, Throwable> {
                val psiFile = PsiManager.getInstance(project).findFile(vFile) ?: return@compute emptyList()
                buildDeps(psiFile, nodeData.ancestorPaths)
            }

            SwingUtilities.invokeLater {
                node.removeAllChildren()
                if (deps.isEmpty()) {
                    model.nodeStructureChanged(node)
                    return@invokeLater
                }
                val seen = mutableSetOf<String>()
                for (dep in deps) {
                    val key = dep.vFile?.path ?: "${dep.kind}:${dep.name}"
                    if (!seen.add(key)) continue
                    val child = DefaultMutableTreeNode(dep)
                    node.add(child)
                    if (dep.vFile != null && !dep.isCycle && isExpandable(dep.vFile)) {
                        child.add(DefaultMutableTreeNode(LoadingNode))
                    }
                }
                model.nodeStructureChanged(node)
            }
        }
    }

    private fun buildDeps(psiFile: PsiFile, ancestors: Set<String>): List<DepNode> {
        val result = mutableListOf<DepNode>()

        // Data area imports (DEFINE DATA section — LOCAL/GLOBAL/PARAMETER USING)
        PsiTreeUtil.findChildOfType(psiFile, NaturalDefineDataPhase::class.java)
            ?.let { PsiTreeUtil.findChildrenOfType(it, NaturalDataAreaBlock::class.java) }
            .orEmpty()
            .forEach { block ->
                val nameEl = NaturalDataAreaUtils.getUsingIdentifier(block) ?: return@forEach
                val kind = when (block.node.firstChildNode?.elementType) {
                    NaturalTypes.KW_LOCAL -> DepKind.LOCAL_USING
                    NaturalTypes.KW_GLOBAL -> DepKind.GLOBAL_USING
                    NaturalTypes.KW_PARAMETER -> DepKind.PARAMETER_USING
                    else -> DepKind.LOCAL_USING
                }
                val resolved = NaturalDataAreaUtils.resolveDataAreaFile(block)?.virtualFile
                result += DepNode(
                    name = nameEl.text,
                    vFile = resolved,
                    kind = kind,
                    isCycle = resolved?.path in ancestors,
                    ancestorPaths = ancestors + (resolved?.path ?: "")
                )
            }

        // CALLNAT references
        PsiTreeUtil.findChildrenOfType(psiFile, NaturalCallnatStatement::class.java).forEach { stmt ->
            val nameEl = tokenAfterKeyword(stmt, NaturalTypes.KW_CALLNAT) ?: return@forEach
            val name = nameEl.text.trim().removeSurrounding("'").removeSurrounding("\"")
            val resolved = NaturalDataAreaUtils.resolveSubprogram(name, project, psiFile)?.virtualFile
            result += DepNode(
                name = name,
                vFile = resolved,
                kind = DepKind.CALLNAT,
                isCycle = resolved?.path in ancestors,
                ancestorPaths = ancestors + (resolved?.path ?: "")
            )
        }

        // INCLUDE references
        PsiTreeUtil.findChildrenOfType(psiFile, NaturalIncludeStatement::class.java).forEach { stmt ->
            val nameEl = tokenAfterKeyword(stmt, NaturalTypes.KW_INCLUDE) ?: return@forEach
            val name = nameEl.text.trim()
            val resolved = NaturalDataAreaUtils.resolveCopyCode(name, project, psiFile)?.virtualFile
            result += DepNode(
                name = name,
                vFile = resolved,
                kind = DepKind.INCLUDE,
                isCycle = resolved?.path in ancestors,
                ancestorPaths = ancestors + (resolved?.path ?: "")
            )
        }

        return result
    }

    private fun tokenAfterKeyword(element: PsiElement, keyword: IElementType): PsiElement? {
        var node = element.node.firstChildNode
        while (node != null) {
            if (node.elementType == keyword) {
                var next = node.treeNext
                while (next != null && next.elementType == TokenType.WHITE_SPACE) next = next.treeNext
                return next?.psi
            }
            node = node.treeNext
        }
        return null
    }

    private fun isExpandable(vFile: VirtualFile) = when (vFile.fileType) {
        is NaturalProgramFileType,
        is NaturalSubProgramFileType,
        is NaturalSubRoutineFileType,
        is NaturalCopyCodeFileType -> true
        else -> false
    }

    private fun rootKind(vFile: VirtualFile) = when (vFile.fileType) {
        is NaturalSubProgramFileType -> DepKind.CALLNAT
        is NaturalCopyCodeFileType -> DepKind.INCLUDE
        is NaturalLDAFileType -> DepKind.LOCAL_USING
        is NaturalGDAFileType -> DepKind.GLOBAL_USING
        is NaturalSubprogramParameterFileType -> DepKind.PARAMETER_USING
        else -> DepKind.ROOT
    }

    override fun dispose() {}
}

// ─── Cell renderer ────────────────────────────────────────────────────────────

private class DepCellRenderer : DefaultTreeCellRenderer() {

    private val unresolved = JBColor(0xC0392B, 0xFF6B6B)
    private val cycle = JBColor(0xE67E22, 0xFFA500)

    override fun getTreeCellRendererComponent(
        tree: JTree, value: Any?, selected: Boolean,
        expanded: Boolean, leaf: Boolean, row: Int, hasFocus: Boolean
    ): Component {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus)
        val data = (value as? DefaultMutableTreeNode)?.userObject
        when (data) {
            is DepNode -> {
                icon = data.kind.icon
                text = buildLabel(data)
                if (!selected) foreground = when {
                    data.isCycle -> cycle
                    data.vFile == null -> unresolved
                    else -> tree.foreground
                }
            }
            LoadingNode -> {
                icon = null
                text = "Loading…"
                if (!selected) foreground = JBColor.GRAY
            }
            else -> {
                icon = null
                text = "Open a Natural file to see its dependencies"
                if (!selected) foreground = JBColor.GRAY
            }
        }
        return this
    }

    private fun buildLabel(dep: DepNode): String {
        val prefix = if (dep.kind != DepKind.ROOT) "${dep.kind.label}  " else ""
        val suffix = when {
            dep.isCycle -> "  ↺ cycle"
            dep.vFile == null -> "  ✗ unresolved"
            else -> ""
        }
        return "$prefix${dep.name}$suffix"
    }
}
