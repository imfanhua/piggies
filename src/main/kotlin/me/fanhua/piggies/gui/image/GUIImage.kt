package me.fanhua.piggies.gui.image

import me.fanhua.piggies.gui.ui.IUICanvas
import org.bukkit.inventory.ItemStack

class GUIImage(private val icons: Array<Array<ItemStack?>>) {

	val lines = icons.size
	val width = icons.getOrNull(0)?.size ?: 0

	fun draw(canvas: IUICanvas, x: Int, y: Int) {
		if (lines == 0 || width == 0) return
		for (diffY in 0 until lines) {
			val items = icons[diffY]
			for (diffX in 0 until width)
				canvas.draw(x + diffX, y + diffY, items[diffX])
		}
	}

}

fun IUICanvas.draw(x: Int, y: Int, image: GUIImage) = apply { image.draw(this, x, y) }
fun IUICanvas.draw(image: GUIImage) = draw(0, 0, image)
