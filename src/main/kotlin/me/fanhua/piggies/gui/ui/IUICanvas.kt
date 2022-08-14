package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import org.bukkit.inventory.ItemStack

interface IUICanvas {

	val size: GUISize
	fun draw(x: Int, y: Int, item: ItemStack?)

	fun diffOf(x: Int, y: Int, width: Int, height: Int): IUICanvas {
		if (x == 0 && y == 0 && width == -1 && height == -1) return this
		val (calcWidth, lines) = size
		return diff(x, y, if (width == -1) calcWidth - x else width, if (height == -1) lines - y else height)
	}

	fun diff(x: Int, y: Int): IUICanvas {
		val (width, lines) = size
		return diff(x, y, width - x, lines - y)
	}

	fun diff(x: Int, y: Int, width: Int, height: Int): IUICanvas
		= UIDiffCanvas(this, x, y, width, height)

	fun drawHLine(x: Int, y: Int, endX: Int, item: ItemStack?) {
		var calcX = x
		var length = endX - calcX
		if (length == 0) return else if (length < 0) {
			calcX = endX
			length = -length
		}
		for (i in 0 until length) draw(calcX + i, y, item)
	}
	fun drawHLine(x: Int, y: Int, item: ItemStack?) = drawHLine(x, y, size.width, item)
	fun drawHLine(x: Int, item: ItemStack?) = drawHLine(x, 0, size.width, item)
	fun drawHLine(item: ItemStack?) = drawHLine(0, size.lines - 1, size.width, item)

	fun drawVLine(x: Int, y: Int, endY: Int, item: ItemStack?) {
		var calcY = y
		var length = endY - calcY
		if (length == 0) return else if (length < 0) {
			calcY = endY
			length = -length
		}
		for (i in 0 until length) draw(x, calcY + i, item)
	}
	fun drawVLine(x: Int, y: Int, item: ItemStack?) = drawVLine(x, y, size.lines, item)
	fun drawVLine(y: Int, item: ItemStack?) = drawVLine(0, y, size.lines, item)
	fun drawVLine(item: ItemStack?) = drawVLine(size.width - 1, 0, size.lines, item)

	fun fill(x: Int, y: Int, width: Int, height: Int, item: ItemStack? = null) {
		for (diffY in 0 until height)
			for (diffX in 0 until width)
				draw(x + diffX, y + diffY, item)
	}
	fun fill(x: Int, y: Int, item: ItemStack? = null) = fill(x, y, size.width - x, size.lines - y, item)
	fun fill(item: ItemStack? = null) = fill(0, 0, item)

}
