package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

abstract class IBasePosUI(x: Int, y: Int) : IBaseUI() {

	var x by observable(x)
	var y by observable(y)

	protected var size: GUISize? = null

	override fun draw(canvas: IUICanvas) {
		redraw = false
		canvas.diff(x, y).let {
			size = it.size
			whenDraw(it)
		}
	}

	override fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean
		= size.let { size ->
			if (size == null || this.x > x || this.y > y) false
			else whenUse(clicker, type, x - this.x, y - this.y, size)
		}

	protected open fun whenDraw(canvas: IUICanvas) {}
	protected open fun whenUse(clicker: Player, type: ActionType, x: Int, y: Int, size: GUISize): Boolean = false

}
