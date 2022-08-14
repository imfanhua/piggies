package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.image.GUIImage
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class UIImage(
	x: Int = 0,
	y: Int = 0,
	image: GUIImage? = null,
) : IBasePosUI(x, y) {

	var image by observable(image)

	override fun draw(canvas: IUICanvas) {
		redraw = false
		image?.draw(canvas, x, y)
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean = false

}

fun IUIContainer.image(
	x: Int = 0,
	y: Int = 0,
	image: GUIImage? = null,
) = add(UIImage(x, y, image))
