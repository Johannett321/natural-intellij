package com.appweb.natural.intellij.mapeditor

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder

// ── Attribute model ──────────────────────────────────────────────────────────

data class ElementAttributes(
    var colorDef: String = "",
    var printMode: String = "",
    var dimensions: String = "0",
    var alignment: String = "",
    var casing: String = "",
    var ioChar: String = "O",
    var representation: String = "",
    var fillChar: String = ".",
    var controlVariable: String = "",
    var dynamicAttrs: String = "",
    var editMask: String = "",
    var numericLength: String = "0",
    var signPosition: String = "off",
    var zeroPrinting: String = "off",
    var helpRoutine: String = "",
    var helpParams: String = "",
    var varName: String = "",
    var varFormat: String = "A",
    var varLength: String = "8",
    var row: Int = 1,
    var col: Int = 1,
    var width: Int = 8
)

// ── Parser / serialiser ──────────────────────────────────────────────────────

object AttributeParser {

    fun fromElement(element: MapElement): ElementAttributes {
        val a = ElementAttributes(row = element.row, col = element.col)
        when (element) {
            is MapElement.Label -> {
                a.varName = element.text
                a.width = element.text.length.coerceAtLeast(1)
                parseLabelAttrString(element.attributes, a)
            }
            is MapElement.Field -> {
                a.varName = element.variableName
                a.width = element.displayLength
                parseDataType(element.dataType, a)
                parseAttrString(element.attributes, a)
            }
        }
        return a
    }

    private fun parseLabelAttrString(attrStr: String, a: ElementAttributes) {
        Regex("""CD=([A-Z]+)""").find(attrStr)?.let { a.colorDef = it.groupValues[1] }
        val letters = attrStr.filter { it.isLetter() && it != 'C' || (it == 'C' && !attrStr.contains("CD=")) }
        a.representation = when {
            letters.contains('I') -> "I"
            letters.contains('B') -> "B"
            attrStr.filter { it.isLetter() }.contains('C') && !attrStr.contains("CD=") -> "C"
            letters.contains('N') -> "N"
            letters.contains('U') -> "U"
            letters.contains('V') -> "V"
            letters.contains('Y') -> "Y"
            else -> ""
        }
    }

    fun toLabelAttrString(a: ElementAttributes): String {
        val sb = StringBuilder()
        if (a.representation.isNotEmpty()) sb.append(a.representation)
        if (a.colorDef.isNotEmpty()) {
            if (sb.isNotEmpty()) sb.append(",")
            sb.append("CD=${a.colorDef}")
        }
        return sb.toString()
    }

    private fun parseDataType(dt: String, a: ElementAttributes) {
        if (dt.isEmpty()) return
        a.varFormat = dt.firstOrNull { it.isLetter() }?.toString() ?: "A"
        a.varLength = dt.filter { it.isDigit() }.ifEmpty { a.width.toString() }
    }

    private fun parseAttrString(attrStr: String, a: ElementAttributes) {
        Regex("""AD=([A-Za-z'.]+)""").find(attrStr)?.let { parseAD(it.groupValues[1], a) }
        Regex("""CV=(\S+?)(?=[\s,)\/])""").find("$attrStr ")?.let { a.controlVariable = it.groupValues[1] }
        Regex("""DY=([^\s,)]+)""").find(attrStr)?.let { a.dynamicAttrs = it.groupValues[1] }
        Regex("""EM=([^\s,)]+)""").find(attrStr)?.let { a.editMask = it.groupValues[1] }
        Regex("""NL=(\d+)""").find(attrStr)?.let { a.numericLength = it.groupValues[1] }
        Regex("""SG=(on|off)""", RegexOption.IGNORE_CASE).find(attrStr)?.let { a.signPosition = it.groupValues[1].lowercase() }
        Regex("""ZP=(on|off)""", RegexOption.IGNORE_CASE).find(attrStr)?.let { a.zeroPrinting = it.groupValues[1].lowercase() }
        Regex("""CD=([A-Z]+)""").find(attrStr)?.let { a.colorDef = it.groupValues[1] }
        Regex("""PM=([A-Z]+)""").find(attrStr)?.let { a.printMode = it.groupValues[1] }
        Regex("""HE='([^']+)'(?:,(\S+))?""").find(attrStr)?.let {
            a.helpRoutine = it.groupValues[1]
            a.helpParams = it.groupValues[2]
        }
    }

    private fun parseAD(ad: String, a: ElementAttributes) {
        val fill = Regex("'([^']*)'").find(ad)?.groupValues?.get(1) ?: ""
        a.fillChar = fill.ifEmpty { "." }
        val letters = ad.filter { it.isLetter() }.uppercase()
        a.ioChar = when {
            letters.contains('A') -> "A"
            letters.contains('M') -> "M"
            else -> "O"
        }
        a.alignment = when {
            letters.contains('L') -> "L"
            letters.contains('R') -> "R"
            letters.contains('Z') -> "Z"
            else -> ""
        }
        a.casing = when {
            letters.contains('T') -> "T"
            letters.contains('W') -> "W"
            else -> ""
        }
        a.representation = when {
            letters.contains('Y') -> "Y"
            letters.contains('I') -> "I"
            letters.contains('B') -> "B"
            letters.contains('C') -> "C"
            letters.contains('N') -> "N"
            letters.contains('U') -> "U"
            letters.contains('V') -> "V"
            else -> ""
        }
    }

    fun toAttrString(a: ElementAttributes): String {
        val sb = StringBuilder()
        val ad = buildString {
            if (a.representation.isNotEmpty()) append(a.representation)
            if (a.alignment.isNotEmpty()) append(a.alignment)
            append(a.ioChar.ifEmpty { "O" })
            if (a.casing.isNotEmpty()) append(a.casing)
        }
        if (ad.isNotEmpty()) {
            sb.append("AD=").append(ad)
            if (a.ioChar == "M" || a.ioChar == "A") sb.append("'${a.fillChar.ifEmpty { "." }}'")
            sb.append(" ")
        }
        if (a.controlVariable.isNotEmpty()) sb.append("CV=${a.controlVariable} ")
        if (a.editMask.isNotEmpty()) sb.append("EM=${a.editMask} ")
        if (a.dynamicAttrs.isNotEmpty()) sb.append("DY=${a.dynamicAttrs} ")
        if (a.helpRoutine.isNotEmpty()) {
            sb.append("HE='${a.helpRoutine}'")
            if (a.helpParams.isNotEmpty()) sb.append(",${a.helpParams}")
            sb.append(" ")
        }
        if (a.colorDef.isNotEmpty()) sb.append("CD=${a.colorDef} ")
        if (a.printMode.isNotEmpty()) sb.append("PM=${a.printMode} ")
        return sb.toString().trimEnd()
    }
}

// ── UI helpers ───────────────────────────────────────────────────────────────

private class CodedCombo(val options: List<Pair<String, String>>) : JComboBox<String>() {
    init { options.forEach { (_, label) -> addItem(label) } }
    var code: String
        get() = options.getOrNull(selectedIndex)?.first ?: ""
        set(v) { selectedIndex = options.indexOfFirst { it.first == v }.takeIf { it >= 0 } ?: 0 }
}

private val PROP_LABEL_FG = Color(0x1A, 0x1A, 0x1A)
private val PROP_ROW_BG = Color(0xFA, 0xFA, 0xFA)
private val PROP_ROW_ALT_BG = Color(0xF2, 0xF2, 0xF2)
private val SECTION_HEADER_BG = Color(0xD8, 0xD8, 0xD8)
private val SECTION_HEADER_FG = Color(0x18, 0x18, 0x18)

private class CollapsibleSection(title: String) : JPanel(BorderLayout()) {
    val content = JPanel().also {
        it.layout = BoxLayout(it, BoxLayout.Y_AXIS)
        it.isOpaque = true
        it.background = PROP_ROW_BG
    }
    private val arrow = JLabel("▼ ").also {
        it.font = it.font.deriveFont(9f)
        it.foreground = SECTION_HEADER_FG
    }
    private var expanded = true
    private var rowCount = 0

    init {
        isOpaque = true
        background = PROP_ROW_BG
        val header = JPanel(FlowLayout(FlowLayout.LEFT, 4, 1)).apply {
            isOpaque = true
            background = SECTION_HEADER_BG
            border = MatteBorder(1, 0, 1, 0, Color(0xB8, 0xB8, 0xB8))
            maximumSize = Dimension(Int.MAX_VALUE, 22)
            preferredSize = Dimension(0, 22)
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            add(arrow)
            add(JLabel(title).also {
                it.font = it.font.deriveFont(Font.BOLD, 11f)
                it.foreground = SECTION_HEADER_FG
            })
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    expanded = !expanded
                    arrow.text = if (expanded) "▼ " else "▶ "
                    content.isVisible = expanded
                    parent?.revalidate(); parent?.repaint()
                }
            })
        }
        add(header, BorderLayout.NORTH)
        add(content, BorderLayout.CENTER)
        maximumSize = Dimension(Int.MAX_VALUE, Int.MAX_VALUE)
    }

    /** Adds a row and returns its panel so the caller can control visibility. */
    fun addRow(label: String, editor: JComponent): JPanel {
        val bg = if (rowCount % 2 == 0) PROP_ROW_BG else PROP_ROW_ALT_BG
        rowCount++
        val row = JPanel(BorderLayout(4, 0)).apply {
            border = EmptyBorder(1, 16, 1, 4)
            maximumSize = Dimension(Int.MAX_VALUE, 26)
            preferredSize = Dimension(0, 26)
            isOpaque = true
            background = bg
        }
        val lbl = JLabel(label).apply {
            preferredSize = Dimension(172, 22)
            font = font.deriveFont(11f)
            foreground = PROP_LABEL_FG
        }
        when (editor) {
            is JTextField -> {
                editor.font = editor.font.deriveFont(11f)
                editor.background = Color.WHITE
                editor.foreground = PROP_LABEL_FG
                editor.isOpaque = true
            }
            is JComboBox<*> -> editor.font = editor.font.deriveFont(11f)
            is JSpinner -> {
                (editor.editor as? JSpinner.DefaultEditor)?.textField?.apply {
                    font = font.deriveFont(11f)
                    background = Color.WHITE
                    foreground = PROP_LABEL_FG
                    isOpaque = true
                }
            }
            is JLabel -> {
                editor.font = editor.font.deriveFont(11f)
                editor.foreground = Color(0x55, 0x55, 0x55)
            }
        }
        row.add(lbl, BorderLayout.WEST)
        row.add(editor, BorderLayout.CENTER)
        content.add(row)
        return row
    }
}

// ── Main panel ───────────────────────────────────────────────────────────────

class NaturalMapPropertiesPanel : JPanel(BorderLayout()) {

    // Appearance
    private val colorDefCombo = CodedCombo(listOf(
        "" to "Default (None)", "BL" to "Blue (BL)", "GR" to "Green (GR)", "NE" to "Neutral (NE)",
        "PI" to "Pink (PI)", "RE" to "Red (RE)", "TU" to "Turquoise (TU)", "YE" to "Yellow (YE)"))
    private val printModeCombo = CodedCombo(listOf(
        "" to "None", "C" to "Alt. character set (C)", "I" to "Inverse direction (I)", "N" to "No hardcopy (N)"))
    // Array
    private val dimensionsField = JBTextField("0")
    // Attribute Definition
    private val alignmentCombo = CodedCombo(listOf(
        "" to "None", "L" to "Left justified (L)", "R" to "Right justified (R)", "Z" to "Leading zeros (Z)"))
    private val casingCombo = CodedCombo(listOf(
        "" to "Default (None)", "T" to "Upper Case (T)", "W" to "Mixed Case (W)"))
    private val ioCharCombo = CodedCombo(listOf(
        "A" to "Input only (A)", "M" to "Output and Input (M)", "O" to "Output only (O)"))
    private val representationCombo = CodedCombo(listOf(
        "" to "Default (None)", "B" to "Blinking (B)", "C" to "Italic (C)", "I" to "Intensified (I)",
        "N" to "Non-display (N)", "U" to "Underline (U)", "V" to "Reverse Video (V)", "Y" to "Dynamic (Y)"))
    // General – label
    private val labelTextField = JBTextField()
    // General – field
    private val cvNameField = JBTextField()
    private val dynamicAttrsField = JBTextField()
    private val editMaskField = JBTextField()
    private val numericLengthField = JBTextField("0")
    private val signPosCombo = CodedCombo(listOf("off" to "off", "on" to "on"))
    private val zeroPrintCombo = CodedCombo(listOf("off" to "off", "on" to "on"))
    // Help
    private val helpRoutineField = JBTextField()
    private val helpParamsField = JBTextField()
    // Position
    private val rowSpinner = JSpinner(SpinnerNumberModel(1, 1, 99, 1))
    private val colSpinner = JSpinner(SpinnerNumberModel(1, 1, 80, 1))
    // Size
    private val widthSpinner = JSpinner(SpinnerNumberModel(8, 1, 80, 1))
    // Status (read-only)
    private val modifiedLabel = JBLabel("false")
    private val errorStatusLabel = JBLabel("None")
    // Variable
    private val varNameField = JBTextField()
    private val varFormatCombo = CodedCombo(listOf(
        "A" to "Alpha (A)", "B" to "Binary (B)", "D" to "Date (D)", "F" to "Float (F)",
        "I" to "Integer (I)", "L" to "Logical (L)", "N" to "Numeric (N)",
        "P" to "Packed (P)", "T" to "Time (T)", "U" to "Unicode (U)"))
    private val varLengthField = JBTextField("8")
    private val varTypeLabel = JBLabel("Data Area")

    private var currentElement: MapElement? = null
    private val changeListeners = mutableListOf<() -> Unit>()
    private val beforeChangeListeners = mutableListOf<() -> Unit>()
    private var suppressUpdate = false
    private var labelSnapshotSaved = false

    // Rows/sections that are visible only for one element type
    private val fieldOnlyRows = mutableListOf<JPanel>()
    private val labelOnlyRows = mutableListOf<JPanel>()
    private val fieldOnlySections = mutableListOf<CollapsibleSection>()

    private val applyBtn = JButton("Apply Changes")
    private val headerLabel = JBLabel("No selection").also {
        it.border = EmptyBorder(4, 8, 4, 8)
        it.font = it.font.deriveFont(Font.ITALIC, 11f)
        it.foreground = Color(0x66, 0x66, 0x66)
    }

    init {
        buildUI()
    }

    private fun buildUI() {
        val container = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            background = Color(0xF8, 0xF8, 0xF8)
        }

        fun section(title: String, block: CollapsibleSection.() -> Unit): CollapsibleSection =
            CollapsibleSection(title).also { it.block(); container.add(it) }

        // ── Appearance ───────────────────────────────────────────────────────
        section("Appearance") {
            addRow("Color Definition (CD)", colorDefCombo)
            fieldOnlyRows += addRow("Print Mode (PM)", printModeCombo)
        }

        // ── Array Info (fields only) ─────────────────────────────────────────
        section("Array Info") {
            addRow("Dimensions", dimensionsField)
        }.also { fieldOnlySections += it }

        // ── Attribute Definition ─────────────────────────────────────────────
        section("Attribute Definition (AD)") {
            fieldOnlyRows += addRow("Alignment", alignmentCombo)
            fieldOnlyRows += addRow("Casing", casingCombo)
            fieldOnlyRows += addRow("I/O Characteristics", ioCharCombo)
            addRow("Representation", representationCombo)
        }

        // ── General ──────────────────────────────────────────────────────────
        section("General") {
            labelOnlyRows += addRow("Label", labelTextField)
            fieldOnlyRows += addRow("Control Variable (CV) Name", cvNameField)
            fieldOnlyRows += addRow("Dynamic Attributes (DY)", dynamicAttrsField)
            fieldOnlyRows += addRow("Edit Mask (EM)", editMaskField)
            fieldOnlyRows += addRow("Numeric Length (NL)", numericLengthField)
            fieldOnlyRows += addRow("Sign Position (SG)", signPosCombo)
            fieldOnlyRows += addRow("Zero Printing (ZP)", zeroPrintCombo)
        }

        // ── Help (fields only) ───────────────────────────────────────────────
        section("Help") {
            addRow("Helproutine Name", helpRoutineField)
            addRow("Help Parameters", helpParamsField)
        }.also { fieldOnlySections += it }

        // ── Position, Size, Status (both) ────────────────────────────────────
        section("Position") {
            addRow("Row", rowSpinner)
            addRow("Column", colSpinner)
        }
        section("Size") {
            addRow("Width", widthSpinner)
        }
        section("Status") {
            addRow("Modified", modifiedLabel)
            addRow("Error Status", errorStatusLabel)
        }

        // ── Variable (fields only) ───────────────────────────────────────────
        section("Variable") {
            addRow("Name", varNameField)
            addRow("Format", varFormatCombo)
            addRow("Length", varLengthField)
            addRow("Type", varTypeLabel)
        }.also { fieldOnlySections += it }

        labelTextField.document.addDocumentListener(object : javax.swing.event.DocumentListener {
            override fun insertUpdate(e: javax.swing.event.DocumentEvent) = onLabelTextChanged()
            override fun removeUpdate(e: javax.swing.event.DocumentEvent) = onLabelTextChanged()
            override fun changedUpdate(e: javax.swing.event.DocumentEvent) = onLabelTextChanged()
        })

        container.add(Box.createVerticalGlue())

        val scroll = JBScrollPane(container).apply {
            border = null
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        }

        applyBtn.isEnabled = false
        applyBtn.addActionListener { applyChanges() }
        val btnPanel = JPanel(FlowLayout(FlowLayout.RIGHT, 4, 4)).also { it.add(applyBtn) }

        val top = JPanel(BorderLayout()).apply {
            border = MatteBorder(0, 0, 1, 0, Color(0xD0, 0xD0, 0xD0))
            add(headerLabel, BorderLayout.CENTER)
        }

        add(top, BorderLayout.NORTH)
        add(scroll, BorderLayout.CENTER)
        add(btnPanel, BorderLayout.SOUTH)
        minimumSize = Dimension(260, 0)
        preferredSize = Dimension(280, 0)
    }

    private fun onLabelTextChanged() {
        if (suppressUpdate) return
        val elem = currentElement as? MapElement.Label ?: return
        if (!labelSnapshotSaved) {
            beforeChangeListeners.forEach { it() }
            labelSnapshotSaved = true
        }
        elem.text = labelTextField.text
        changeListeners.forEach { it() }
    }

    fun addChangeListener(listener: () -> Unit) = changeListeners.add(listener)
    fun addBeforeChangeListener(listener: () -> Unit) = beforeChangeListeners.add(listener)

    fun showMultiSelection(count: Int) {
        currentElement = null
        applyBtn.isEnabled = false
        headerLabel.text = "$count elements selected"
        headerLabel.font = headerLabel.font.deriveFont(Font.ITALIC)
        fieldOnlySections.forEach { it.isVisible = false }
        fieldOnlyRows.forEach { it.isVisible = false }
        labelOnlyRows.forEach { it.isVisible = false }
    }

    fun showElement(element: MapElement?) {
        currentElement = element
        labelSnapshotSaved = false
        applyBtn.isEnabled = element != null

        if (element == null) {
            headerLabel.text = "No selection"
            headerLabel.font = headerLabel.font.deriveFont(Font.ITALIC)
            fieldOnlySections.forEach { it.isVisible = false }
            fieldOnlyRows.forEach { it.isVisible = false }
            labelOnlyRows.forEach { it.isVisible = false }
            return
        }

        val isField = element is MapElement.Field
        fieldOnlySections.forEach { it.isVisible = isField }
        fieldOnlyRows.forEach { it.isVisible = isField }
        labelOnlyRows.forEach { it.isVisible = !isField }

        headerLabel.font = headerLabel.font.deriveFont(Font.BOLD)
        headerLabel.text = when (element) {
            is MapElement.Label -> "Label: '${element.text.take(24)}'"
            is MapElement.Field -> "Field: ${element.variableName}"
        }

        val a = AttributeParser.fromElement(element)

        colorDefCombo.code = a.colorDef
        representationCombo.code = a.representation
        rowSpinner.value = a.row
        colSpinner.value = a.col
        widthSpinner.value = a.width

        if (!isField) {
            suppressUpdate = true
            labelTextField.text = (element as MapElement.Label).text
            suppressUpdate = false
        } else {
            printModeCombo.code = a.printMode
            alignmentCombo.code = a.alignment
            casingCombo.code = a.casing
            ioCharCombo.code = a.ioChar
            dimensionsField.text = a.dimensions
            cvNameField.text = a.controlVariable
            dynamicAttrsField.text = a.dynamicAttrs
            editMaskField.text = a.editMask
            numericLengthField.text = a.numericLength
            signPosCombo.code = a.signPosition
            zeroPrintCombo.code = a.zeroPrinting
            helpRoutineField.text = a.helpRoutine
            helpParamsField.text = a.helpParams
            varNameField.text = a.varName
            varFormatCombo.code = a.varFormat
            varLengthField.text = a.varLength
        }

        revalidate()
        repaint()
    }

    private fun applyChanges() {
        val elem = currentElement ?: return
        beforeChangeListeners.forEach { it() }
        elem.row = rowSpinner.value as Int
        elem.col = colSpinner.value as Int

        when (elem) {
            is MapElement.Label -> {
                val newText = labelTextField.text
                elem.text = newText
                elem.attributes = AttributeParser.toLabelAttrString(ElementAttributes(
                    representation = representationCombo.code,
                    colorDef = colorDefCombo.code
                ))
                // Keep header in sync
                headerLabel.text = "Label: '${newText.take(24)}'"
            }
            is MapElement.Field -> {
                elem.displayLength = widthSpinner.value as Int
                elem.variableName = varNameField.text.trim()
                val fmt = varFormatCombo.code
                val len = varLengthField.text.toIntOrNull() ?: elem.displayLength
                elem.dataType = "$fmt${len.toString().padStart(3, '0')}"
                elem.attributes = AttributeParser.toAttrString(ElementAttributes(
                    colorDef = colorDefCombo.code, printMode = printModeCombo.code,
                    alignment = alignmentCombo.code, casing = casingCombo.code,
                    ioChar = ioCharCombo.code, representation = representationCombo.code,
                    controlVariable = cvNameField.text.trim(),
                    dynamicAttrs = dynamicAttrsField.text.trim(),
                    editMask = editMaskField.text.trim(),
                    numericLength = numericLengthField.text.trim(),
                    signPosition = signPosCombo.code, zeroPrinting = zeroPrintCombo.code,
                    helpRoutine = helpRoutineField.text.trim(),
                    helpParams = helpParamsField.text.trim()
                ))
            }
        }
        changeListeners.forEach { it() }
    }
}
