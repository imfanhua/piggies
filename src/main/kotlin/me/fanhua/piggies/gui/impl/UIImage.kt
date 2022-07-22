package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.gui.GUIImage
import me.fanhua.piggies.gui.ui.IUI
import me.fanhua.piggies.gui.ui.IUICanvas
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class UIImage(x: Int = 0, y: Int = 0, image: GUIImage? = null) : IUI {

	var x = x
		set(value) {
			if (field == value) return
			field = value
			redraw = true
		}

	var y = y
		set(value) {
			if (field == value) return
			field = value
			redraw = true
		}

	var image = image
		set(value) {
			if (field == value) return
			field = value
			redraw = true
		}

	private var redraw = true

	override fun redraw() = redraw

	override fun update() {}
	override fun draw(canvas: IUICanvas) {
		redraw = false
		image?.draw(canvas, x, y)
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean = false

}
