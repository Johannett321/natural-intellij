package com.appweb.natural.intellij.mapeditor

import com.intellij.ui.components.JBScrollPane
import java.awt.*
import java.awt.event.*
import java.util.Collections
import javax.swing.*

class NaturalMapCanvas(private var model: MapModel) : JPanel() {

    companion object {
        private const val CELL_W = 9
        private const val CELL_H = 16
        private const val MARGIN_TOP = 24
        private const val MARGIN_LEFT = 28
        private const val PADDING = 8

        private val BG = Color(0xF2, 0xF2, 0xF2)
        private val GRID_COLOR = Color(0xD8, 0xD8, 0xD8)
        private val LABEL_FG = Color(0x1A, 0x1A, 0x1A)
        private val FIELD_BG = Color(0xFF, 0xFF, 0xFF)
        private val FIELD_FG = Color(0x00, 0x40, 0x90)
        private val FIELD_BORDER = Color(0xA0, 0xA8, 0xB8)
        private val RULER_FG = Color(0x80, 0x80, 0x80)
        private val SELECTED_COLOR = Color(0x20, 0x80, 0xFF)
        private val BORDER_COLOR = Color(0xB4, 0xB4, 0xB4)
        private val INTENSIFIED_FG = Color(0x00, 0x00, 0x00)
    }

    // Identity-based set so that mutating row/col (which changes hashCode on data classes)
    // doesn't corrupt the set's bucket structure.
    val selectedElements: MutableSet<MapElement> = Collections.newSetFromMap(java.util.IdentityHashMap<MapElement, Boolean>())

    private val selectionListeners = mutableListOf<(MapElement?) -> Unit>()

    private var dragElement: MapElement? = null
    private var dragOffsetCol = 0
    private var dragOffsetRow = 0
    private var isDragging = false
    // IdentityHashMap + separate anchor start so lookups survive row/col mutation.
    private var dragStartPositions: java.util.IdentityHashMap<MapElement, Pair<Int, Int>> = java.util.IdentityHashMap()
    private var dragAnchorStartCol = 0
    private var dragAnchorStartRow = 0

    private var inlineEditor: JTextField? = null
    private val modelChangeListeners = mutableListOf<() -> Unit>()
    private var clipboard: List<MapElement> = emptyList()

    private var rubberBandStart: Point? = null
    private var rubberBandRect: Rectangle? = null

    private val undoStack = ArrayDeque<MapModel>()
    private val redoStack = ArrayDeque<MapModel>()
    private var savedSnapshotForCurrentDrag = false

    private val monoFont = Font(Font.MONOSPACED, Font.PLAIN, 12)

    init {
        isOpaque = true
        background = BG
        cursor = Cursor.getDefaultCursor()
        layout = null
        isFocusable = true
        updatePreferredSize()
        setupMouseListeners()
        setupKeyBindings()
    }

    private fun updatePreferredSize() {
        val w = MARGIN_LEFT + model.lineSize * CELL_W + PADDING * 2
        val h = MARGIN_TOP + model.pageSize * CELL_H + PADDING * 2
        preferredSize = Dimension(w, h)
    }

    fun setModel(newModel: MapModel) {
        undoStack.clear()
        redoStack.clear()
        model = newModel
        selectedElements.clear()
        updatePreferredSize()
        repaint()
        selectionListeners.forEach { it(null) }
    }

    fun getModel(): MapModel = model

    fun addSelectionListener(listener: (MapElement?) -> Unit) {
        selectionListeners.add(listener)
    }

    fun addModelChangeListener(listener: () -> Unit) {
        modelChangeListeners.add(listener)
    }

    fun saveSnapshot() {
        undoStack.addLast(model.deepCopy())
        if (undoStack.size > 50) undoStack.removeFirst()
        redoStack.clear()
    }

    fun undo() {
        if (undoStack.isEmpty()) return
        redoStack.addLast(model.deepCopy())
        restoreModel(undoStack.removeLast())
    }

    fun redo() {
        if (redoStack.isEmpty()) return
        undoStack.addLast(model.deepCopy())
        restoreModel(redoStack.removeLast())
    }

    private fun restoreModel(snapshot: MapModel) {
        inlineEditor?.let { remove(it); inlineEditor = null }
        model = snapshot
        selectedElements.clear()
        updatePreferredSize()
        notifySelectionChanged()
        modelChangeListeners.forEach { it() }
        repaint()
    }

    private fun MapModel.deepCopy() = copy(
        elements = elements.map { elem ->
            when (elem) {
                is MapElement.Label -> elem.copy()
                is MapElement.Field -> elem.copy()
            }
        }.toMutableList()
    )

    private fun notifySelectionChanged() {
        selectionListeners.forEach { it(selectedElements.singleOrNull()) }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB)
        g2.font = monoFont

        g2.color = BG
        g2.fillRect(0, 0, width, height)

        g2.color = BORDER_COLOR
        g2.drawRect(MARGIN_LEFT - 1, MARGIN_TOP - 1,
            model.lineSize * CELL_W + 1, model.pageSize * CELL_H + 1)

        g2.color = GRID_COLOR
        for (col in 10..model.lineSize step 10) {
            val x = MARGIN_LEFT + col * CELL_W
            g2.drawLine(x, MARGIN_TOP, x, MARGIN_TOP + model.pageSize * CELL_H)
        }

        g2.color = RULER_FG
        g2.font = Font(Font.MONOSPACED, Font.PLAIN, 10)
        for (col in 1..model.lineSize step 10) {
            val x = MARGIN_LEFT + (col - 1) * CELL_W
            g2.drawString(col.toString(), x, MARGIN_TOP - 6)
        }
        for (row in 1..model.pageSize) {
            val y = MARGIN_TOP + (row - 1) * CELL_H + CELL_H - 3
            g2.drawString(row.toString().padStart(2), 2, y)
        }

        g2.font = monoFont

        for (element in model.elements) {
            drawElement(g2, element, element in selectedElements)
        }

        rubberBandRect?.let { r ->
            g2.color = Color(0x20, 0x80, 0xFF, 40)
            g2.fillRect(r.x, r.y, r.width, r.height)
            g2.color = Color(0x20, 0x80, 0xFF, 180)
            g2.drawRect(r.x, r.y, r.width, r.height)
        }
    }

    private fun drawElement(g2: Graphics2D, element: MapElement, selected: Boolean) {
        val x = MARGIN_LEFT + (element.col - 1) * CELL_W
        val y = MARGIN_TOP + (element.row - 1) * CELL_H

        when (element) {
            is MapElement.Label -> {
                val text = element.text
                val intensified = element.attributes.contains("I", ignoreCase = true)
                g2.color = if (intensified) INTENSIFIED_FG else LABEL_FG
                g2.drawString(text, x, y + CELL_H - 3)
                if (selected) {
                    g2.color = SELECTED_COLOR
                    g2.drawRect(x - 1, y, text.length.coerceAtLeast(1) * CELL_W + 1, CELL_H)
                }
            }
            is MapElement.Field -> {
                val len = element.displayLength.coerceAtLeast(1)
                g2.color = FIELD_BG
                g2.fillRect(x, y + 1, len * CELL_W, CELL_H - 2)
                g2.color = if (selected) SELECTED_COLOR else FIELD_BORDER
                g2.drawRect(x, y + 1, len * CELL_W, CELL_H - 2)
                val display = element.variableName.take(len).padEnd(len, ' ')
                g2.color = FIELD_FG
                g2.drawString(display.take(len), x + 1, y + CELL_H - 3)
            }
        }
    }

    private fun elementAt(x: Int, y: Int): MapElement? {
        val col = (x - MARGIN_LEFT) / CELL_W + 1
        val row = (y - MARGIN_TOP) / CELL_H + 1
        return model.elements.lastOrNull { elem ->
            elem.row == row && col >= elem.col && col < elem.col + elementWidth(elem)
        }
    }

    private fun elementWidth(elem: MapElement): Int = when (elem) {
        is MapElement.Label -> elem.text.length.coerceAtLeast(1)
        is MapElement.Field -> elem.displayLength.coerceAtLeast(1)
    }

    private fun elementBounds(elem: MapElement) = Rectangle(
        MARGIN_LEFT + (elem.col - 1) * CELL_W,
        MARGIN_TOP + (elem.row - 1) * CELL_H,
        elementWidth(elem) * CELL_W,
        CELL_H
    )

    private fun pixelToCol(x: Int) = ((x - MARGIN_LEFT).toDouble() / CELL_W + 1).toInt()
        .coerceIn(1, model.lineSize)
    private fun pixelToRow(y: Int) = ((y - MARGIN_TOP).toDouble() / CELL_H + 1).toInt()
        .coerceIn(1, model.pageSize)

    private fun beginDrag(anchor: MapElement, mouseX: Int, mouseY: Int) {
        dragElement = anchor
        isDragging = false
        savedSnapshotForCurrentDrag = false
        dragOffsetCol = pixelToCol(mouseX) - anchor.col
        dragOffsetRow = pixelToRow(mouseY) - anchor.row
        dragAnchorStartCol = anchor.col
        dragAnchorStartRow = anchor.row
        dragStartPositions = java.util.IdentityHashMap<MapElement, Pair<Int, Int>>().also { map ->
            selectedElements.forEach { map[it] = Pair(it.row, it.col) }
        }
    }

    private fun setupMouseListeners() {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                requestFocusInWindow()
                val elem = elementAt(e.x, e.y)

                if (e.isShiftDown) {
                    if (elem != null) {
                        if (!selectedElements.remove(elem)) selectedElements.add(elem)
                    }
                    dragElement = null
                } else {
                    when {
                        elem == null -> {
                            selectedElements.clear()
                            dragElement = null
                            rubberBandStart = Point(e.x, e.y)
                            rubberBandRect = null
                        }
                        elem in selectedElements -> beginDrag(elem, e.x, e.y)
                        else -> {
                            selectedElements.clear()
                            selectedElements.add(elem)
                            beginDrag(elem, e.x, e.y)
                        }
                    }
                }

                notifySelectionChanged()
                repaint()
            }

            override fun mouseReleased(e: MouseEvent) {
                if (rubberBandRect != null) {
                    rubberBandStart = null
                    rubberBandRect = null
                    // selectedElements already up to date from live drag updates
                    repaint()
                    return
                }
                rubberBandStart = null
                if (isDragging) {
                    isDragging = false
                    modelChangeListeners.forEach { it() }
                }
                dragElement = null
            }

            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    val elem = elementAt(e.x, e.y) ?: return
                    startInlineEdit(elem)
                }
            }
        })

        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val start = rubberBandStart
                if (start != null) {
                    val r = Rectangle(
                        minOf(start.x, e.x), minOf(start.y, e.y),
                        Math.abs(e.x - start.x), Math.abs(e.y - start.y)
                    )
                    rubberBandRect = r
                    selectedElements.clear()
                    model.elements.forEach { elem ->
                        if (r.intersects(elementBounds(elem))) selectedElements.add(elem)
                    }
                    notifySelectionChanged()
                    repaint()
                    return
                }

                val drag = dragElement ?: return
                if (!savedSnapshotForCurrentDrag) {
                    saveSnapshot()
                    savedSnapshotForCurrentDrag = true
                }
                isDragging = true
                val newCol = (pixelToCol(e.x) - dragOffsetCol).coerceIn(1, model.lineSize)
                val newRow = (pixelToRow(e.y) - dragOffsetRow).coerceIn(1, model.pageSize)
                if (selectedElements.size <= 1) {
                    drag.col = newCol
                    drag.row = newRow
                } else {
                    val deltaCol = newCol - dragAnchorStartCol
                    val deltaRow = newRow - dragAnchorStartRow
                    for ((elem, start) in dragStartPositions) {
                        elem.col = (start.second + deltaCol).coerceIn(1, model.lineSize)
                        elem.row = (start.first + deltaRow).coerceIn(1, model.pageSize)
                    }
                }
                repaint()
            }
        })
    }

    private fun setupKeyBindings() {
        val shortcut = Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx
        val im = getInputMap(JComponent.WHEN_FOCUSED)
        val am = actionMap

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete-elem")
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete-elem")
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, shortcut), "copy-elem")
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, shortcut), "paste-elem")
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcut), "undo")
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcut or InputEvent.SHIFT_DOWN_MASK), "redo")

        am.put("delete-elem", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { deleteSelected() }
        })
        am.put("undo", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { undo() }
        })
        am.put("redo", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { redo() }
        })
        am.put("copy-elem", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                clipboard = selectedElements.map { copyElement(it) }
            }
        })
        am.put("paste-elem", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                if (clipboard.isEmpty()) return
                saveSnapshot()
                selectedElements.clear()
                clipboard.forEach { src ->
                    val pasted = copyElement(src, rowOffset = 1, colOffset = 1)
                    model.elements.add(pasted)
                    selectedElements.add(pasted)
                }
                notifySelectionChanged()
                modelChangeListeners.forEach { it() }
                repaint()
            }
        })
    }

    private fun copyElement(elem: MapElement, rowOffset: Int = 0, colOffset: Int = 0): MapElement =
        when (elem) {
            is MapElement.Label -> elem.copy(
                row = (elem.row + rowOffset).coerceIn(1, model.pageSize),
                col = (elem.col + colOffset).coerceIn(1, model.lineSize)
            )
            is MapElement.Field -> elem.copy(
                row = (elem.row + rowOffset).coerceIn(1, model.pageSize),
                col = (elem.col + colOffset).coerceIn(1, model.lineSize)
            )
        }

    private fun startInlineEdit(element: MapElement) {
        inlineEditor?.let { remove(it); inlineEditor = null }
        val x = MARGIN_LEFT + (element.col - 1) * CELL_W
        val y = MARGIN_TOP + (element.row - 1) * CELL_H + 1
        val (initialText, w) = when (element) {
            is MapElement.Label -> element.text to (element.text.length.coerceAtLeast(4) + 2) * CELL_W
            is MapElement.Field -> element.variableName to element.displayLength.coerceAtLeast(4) * CELL_W
        }
        val tf = JTextField(initialText)
        tf.font = monoFont
        tf.setBounds(x, y, w, CELL_H - 1)
        tf.border = BorderFactory.createLineBorder(SELECTED_COLOR, 1)
        tf.background = Color(0xFF, 0xFF, 0xE8)
        tf.foreground = Color(0x00, 0x00, 0x00)
        tf.addActionListener { commitInlineEdit(element, tf) }
        tf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent) { commitInlineEdit(element, tf) }
        })
        tf.getInputMap(JComponent.WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel-edit")
        tf.actionMap.put("cancel-edit", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { cancelInlineEdit(tf) }
        })
        inlineEditor = tf
        add(tf)
        tf.requestFocusInWindow()
        tf.selectAll()
        repaint()
    }

    private fun commitInlineEdit(element: MapElement, tf: JTextField) {
        if (inlineEditor !== tf) return
        val text = tf.text
        if (text.isNotEmpty()) {
            saveSnapshot()
            when (element) {
                is MapElement.Label -> element.text = text
                is MapElement.Field -> element.variableName = text.trim().uppercase()
            }
        }
        remove(tf)
        inlineEditor = null
        notifySelectionChanged()
        modelChangeListeners.forEach { it() }
        repaint()
    }

    private fun cancelInlineEdit(tf: JTextField) {
        if (inlineEditor !== tf) return
        remove(tf)
        inlineEditor = null
        repaint()
    }

    fun deleteSelected() {
        if (selectedElements.isEmpty()) return
        saveSnapshot()
        model.elements.removeAll(selectedElements)
        selectedElements.clear()
        selectionListeners.forEach { it(null) }
        modelChangeListeners.forEach { it() }
        repaint()
    }

    fun addLabel(text: String, row: Int = 1, col: Int = 1) {
        saveSnapshot()
        val label = MapElement.Label(row, col, text)
        model.elements.add(label)
        selectedElements.clear()
        selectedElements.add(label)
        selectionListeners.forEach { it(label) }
        repaint()
    }

    fun addField(varName: String, displayLength: Int = 8, row: Int = 1, col: Int = 1) {
        saveSnapshot()
        val field = MapElement.Field(row, col, varName, displayLength)
        model.elements.add(field)
        selectedElements.clear()
        selectedElements.add(field)
        selectionListeners.forEach { it(field) }
        repaint()
    }
}

fun NaturalMapCanvas.wrapInScrollPane(): JBScrollPane {
    return JBScrollPane(this).also { scroll ->
        scroll.border = BorderFactory.createEmptyBorder()
        scroll.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        scroll.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    }
}
