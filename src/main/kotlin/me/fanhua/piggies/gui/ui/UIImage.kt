package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUIImage
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import kotlin.properties.Delegates

class UIImage(
	x: Int = 0,
	y: Int = 0,
	image: GUIImage? = null,
) : IUI {

	var x by Delegates.observable(x) { _, _, _ -> redraw = true }
	var y by Delegates.observable(y) { _, _, _ -> redraw = true }
	var image by Delegates.observable(image) { _, _, _ -> redraw = true }

	private var redraw = true

	override fun redraw(): Boolean = redraw

	override fun update() {}
	override fun draw(canvas: IUICanvas) {
		redraw = false
		image?.draw(canvas, x, y)
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean = false

}
