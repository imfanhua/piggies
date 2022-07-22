package me.fanhua.piggies.gui

import kotlinx.serialization.Serializable

@Serializable
data class GUISize(val width: Int, val lines: Int) {

	val size = width * lines

	operator fun get(slot: Int): Pair<Int, Int> = (slot / width).let { slot - (it * width) to it }

	operator fun get(x: Int, y: Int): Int = y * width + x

}
