package com.appweb.natural.intellij.mapeditor

data class MapModel(
    val pageSize: Int = 24,
    val lineSize: Int = 80,
    val elements: MutableList<MapElement> = mutableListOf(),
    val rawBeforeInputBody: String = "",
    val rawAfterInputBody: String = "",
    val cvVariable: String = ""
)

sealed class MapElement {
    abstract var row: Int
    abstract var col: Int

    data class Label(
        override var row: Int,
        override var col: Int,
        var text: String,
        var attributes: String = ""
    ) : MapElement()

    data class Field(
        override var row: Int,
        override var col: Int,
        var variableName: String,
        var displayLength: Int = 8,
        var attributes: String = "AD=DLMT'.' ",
        var dataType: String = "A008"
    ) : MapElement()
}
