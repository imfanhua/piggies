package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class UIButton(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	var handler: ((UIButton, Player, ActionType) -> Unit)? = null,
) : IBasePosUI(x, y) {

	var icon by observable(icon)

	override fun draw(canvas: IUICanvas) {
		redraw = false
		icon?.let { canvas.draw(x, y, it) }
	}

	override fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean
		= if (this.x != x || this.y != y) false
		else true.apply { handler?.invoke(this@UIButton, clicker, type) }

}

fun IUIContainer.button(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	handler: ((UIButton, Player, ActionType) -> Unit)? = null,
) = add(UIButton(x, y, icon, handler))
