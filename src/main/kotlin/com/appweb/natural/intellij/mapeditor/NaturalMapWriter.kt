package com.appweb.natural.intellij.mapeditor

object NaturalMapWriter {

    fun write(model: MapModel): String {
        val sb = StringBuilder()
        sb.append(model.rawBeforeInputBody)
        if (!model.rawBeforeInputBody.endsWith("\n")) sb.append("\n")
        sb.append(generateInputBody(model))
        sb.append("\n")
        sb.append(model.rawAfterInputBody)
        if (!model.rawAfterInputBody.endsWith("\n")) sb.append("\n")
        return sb.toString()
    }

    private fun generateInputBody(model: MapModel): String {
        val sb = StringBuilder()
        if (model.elements.isEmpty()) return sb.toString()

        val sortedElements = model.elements.sortedWith(compareBy({ it.row }, { it.col }))

        var prevRow = 1
        var firstElement = true

        for (element in sortedElements) {
            if (!firstElement || element.row > 1) {
                val rowDiff = element.row - prevRow
                repeat(rowDiff) { sb.appendLine("/") }
            }
            prevRow = element.row
            firstElement = false
            sb.appendLine(formatElement(element))
        }

        return sb.toString().trimEnd('\n')
    }

    private fun formatElement(element: MapElement): String {
        val col = element.col.toString().padStart(3, '0')
        return when (element) {
            is MapElement.Label -> {
                val escaped = element.text.replace("'", "''")
                val attrs = if (element.attributes.isNotEmpty()) "(${element.attributes})" else ""
                " ${col}T '${escaped}'${attrs}"
            }
            is MapElement.Field -> {
                val len = element.displayLength.toString().padStart(3, '0')
                val attrs = if (element.attributes.isNotEmpty()) "(${element.attributes} AL=${len}  )"
                            else "(AD=DLMT'.' AL=${len}  )"
                val dataType = element.dataType.ifEmpty { "A${len}" }
                " ${col}T ${element.variableName}  ${attrs} /*.99D${len} ${dataType}."
            }
        }
    }
}
