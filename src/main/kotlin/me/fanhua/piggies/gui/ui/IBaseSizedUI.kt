package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

abstract class IBaseSizedUI(x: Int, y: Int, width: Int, height: Int) : IBasePosUI(x, y) {

	var width by observable(width)
	var height by observable(height)

	override fun draw(canvas: IUICanvas) {
		redraw = false
		canvas.diffOf(x, y, width, height).let {
			size = it.size
			whenDraw(it)
		}
	}

	override fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean
		= size.let { size ->
			if (size?.isInside(x - this.x, y - this.y) != true) false
			else whenUse(clicker, type, x - this.x, y - this.y, size)
		}

}
