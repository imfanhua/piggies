package me.fanhua.piggies.gui

import me.fanhua.piggies.gui.ui.IUICanvas
import org.bukkit.inventory.ItemStack

class GUIImage(private val icons: Array<Array<ItemStack?>>) {

	fun draw(canvas: IUICanvas, x: Int, y: Int) {
		val length = icons.size
		for (l in 0 until length) {
			val items = icons[l]
			for (i in items.indices) canvas.draw(x + i, y + l, items[i])
		}
	}

}