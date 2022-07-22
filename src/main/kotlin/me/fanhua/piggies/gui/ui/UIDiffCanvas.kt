package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import org.bukkit.inventory.ItemStack

class UIDiffCanvas(
	private val canvas: IUICanvas,
	private val x: Int,
	private val y: Int,
	width: Int,
	height: Int
) : IUICanvas {

	override val size: GUISize = GUISize(width, height)

	override fun draw(x: Int, y: Int, item: ItemStack?) {
		canvas.draw(this.x + x, this.y + y, item)
	}

	override fun diff(x: Int, y: Int, width: Int, height: Int): IUICanvas {
		return UIDiffCanvas(canvas, this.x + x, this.y + y, width, height)
	}

}