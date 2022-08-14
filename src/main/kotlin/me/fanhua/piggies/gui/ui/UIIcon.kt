package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class UIIcon(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
) : IBasePosUI(x, y) {

	var icon by observable(icon)

	override fun draw(canvas: IUICanvas) {
		redraw = false
		icon?.let { canvas.draw(x, y, it) }
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean = false

}

fun IUIContainer.icon(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
) = add(UIIcon(x, y, icon))
