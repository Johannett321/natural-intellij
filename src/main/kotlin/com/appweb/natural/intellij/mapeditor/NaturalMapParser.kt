package com.appweb.natural.intellij.mapeditor

object NaturalMapParser {

    fun parse(content: String): MapModel {
        val lines = content.lines()
        var pageSize = 24
        var lineSize = 80
        val elements = mutableListOf<MapElement>()
        var cvVariable = ""

        val beforeInputBodyLines = mutableListOf<String>()
        val afterInputBodyLines = mutableListOf<String>()

        var state = ParseState.BEFORE_INPUT_BODY
        var currentRow = 1
        var inputParenDepth = 0
        var i = 0

        while (i < lines.size) {
            val line = lines[i]
            val trimmed = line.trim()

            when (state) {
                ParseState.BEFORE_INPUT_BODY -> {
                    beforeInputBodyLines.add(line)

                    val psMatch = Regex("FORMAT.*PS=(\\d+).*LS=(\\d+)").find(trimmed)
                    if (psMatch != null) {
                        pageSize = psMatch.groupValues[1].toIntOrNull() ?: 24
                        lineSize = psMatch.groupValues[2].toIntOrNull() ?: 80
                    }

                    if (trimmed.startsWith("INPUT", ignoreCase = true) ||
                        trimmed.startsWith("WRITE", ignoreCase = true)) {
                        val cvMatch = Regex("CV=(\\S+)").find(trimmed)
                        cvVariable = cvMatch?.groupValues?.get(1)
                            ?.trimEnd(',', ' ', ')', '/') ?: ""

                        val opens = trimmed.count { it == '(' }
                        val closes = trimmed.count { it == ')' }
                        inputParenDepth = opens - closes

                        if (inputParenDepth <= 0) {
                            state = ParseState.MAP_BODY
                            currentRow = 1
                        } else {
                            state = ParseState.INPUT_OPTIONS
                        }
                    }
                    i++
                }

                ParseState.INPUT_OPTIONS -> {
                    beforeInputBodyLines.add(line)
                    val opens = line.count { it == '(' }
                    val closes = line.count { it == ')' }
                    inputParenDepth += opens - closes
                    if (inputParenDepth <= 0) {
                        state = ParseState.MAP_BODY
                        currentRow = 1
                    }
                    i++
                }

                ParseState.MAP_BODY -> {
                    when {
                        trimmed.startsWith("* MAP2: VALIDATION", ignoreCase = true) ||
                        trimmed.startsWith("RULEVAR", ignoreCase = true) ||
                        trimmed.equals("END", ignoreCase = true) -> {
                            state = ParseState.AFTER_INPUT_BODY
                            afterInputBodyLines.add(line)
                            i++
                        }
                        trimmed.matches(Regex("/\\s*")) -> {
                            currentRow++
                            i++
                        }
                        trimmed.startsWith("/**") || trimmed.startsWith("*") || trimmed.isEmpty() -> {
                            i++
                        }
                        else -> {
                            val posMatch = Regex("^\\s*(\\d{1,3})T\\s+(.+)").find(line)
                            if (posMatch != null) {
                                val col = posMatch.groupValues[1].toInt()
                                val restOfLine = posMatch.groupValues[2]

                                val fullContent = StringBuilder(restOfLine)
                                var j = i + 1
                                while (j < lines.size) {
                                    val nextLine = lines[j]
                                    val nextTrimmed = nextLine.trim()
                                    if (nextTrimmed.isNotEmpty() &&
                                        !nextTrimmed.startsWith("*") &&
                                        !nextTrimmed.startsWith("/**") &&
                                        !nextTrimmed.matches(Regex("/\\s*")) &&
                                        !Regex("^\\s*\\d{1,3}T\\s").containsMatchIn(nextLine) &&
                                        nextLine.isNotEmpty() &&
                                        (nextLine[0] == ' ' || nextLine[0] == '\t')) {
                                        fullContent.append(" ").append(nextTrimmed)
                                        j++
                                    } else {
                                        break
                                    }
                                }

                                parseElement(col, currentRow, fullContent.toString())?.let {
                                    elements.add(it)
                                }
                                i = j
                            } else {
                                i++
                            }
                        }
                    }
                }

                ParseState.AFTER_INPUT_BODY -> {
                    afterInputBodyLines.add(line)
                    i++
                }
            }
        }

        return MapModel(
            pageSize = pageSize,
            lineSize = lineSize,
            elements = elements,
            rawBeforeInputBody = beforeInputBodyLines.joinToString("\n"),
            rawAfterInputBody = afterInputBodyLines.joinToString("\n"),
            cvVariable = cvVariable
        )
    }

    private fun parseElement(col: Int, row: Int, content: String): MapElement? {
        val trimmed = content.trim()
        if (trimmed.isEmpty()) return null
        return if (trimmed.startsWith("'")) parseLabel(col, row, trimmed)
        else parseField(col, row, trimmed)
    }

    private fun parseLabel(col: Int, row: Int, content: String): MapElement.Label? {
        val sb = StringBuilder()
        var i = 1
        while (i < content.length) {
            when {
                content[i] == '\'' && i + 1 < content.length && content[i + 1] == '\'' -> {
                    sb.append('\''); i += 2
                }
                content[i] == '\'' -> { i++; break }
                else -> { sb.append(content[i]); i++ }
            }
        }
        val attrs = if (i < content.length && content[i] == '(') {
            val end = content.indexOf(')', i)
            if (end > i) content.substring(i + 1, end) else ""
        } else ""
        return MapElement.Label(row, col, sb.toString(), attrs)
    }

    private fun parseField(col: Int, row: Int, content: String): MapElement.Field? {
        val varName = content.split(Regex("\\s+"))[0].trim()
        if (varName.isEmpty() || varName.startsWith("/")) return null

        val alMatch = Regex("\\bAL=(\\d+)").find(content)
        val alLength = alMatch?.groupValues?.get(1)?.toIntOrNull()

        val commentMatch = Regex("/\\*\\.(\\w{2})([DSV])(\\d{3})\\s+(\\S+)").find(content)
        val commentLength = commentMatch?.groupValues?.get(3)?.toIntOrNull()
        val dataType = commentMatch?.groupValues?.get(4)?.trimEnd('.') ?: ""

        val displayLength = alLength ?: commentLength ?: 8

        val attrMatch = Regex("\\((?=[^)]*(?:AD|CV|HE|EM)=)[^)]+\\)").find(content)
        val attributes = attrMatch?.value?.removeSurrounding("(", ")") ?: ""

        return MapElement.Field(
            row = row, col = col,
            variableName = varName,
            displayLength = displayLength,
            attributes = attributes,
            dataType = dataType
        )
    }

    private enum class ParseState { BEFORE_INPUT_BODY, INPUT_OPTIONS, MAP_BODY, AFTER_INPUT_BODY }
}
