package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import kotlin.properties.Delegates

class UIButton(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	var handler: BiConsumer<Player, ClickType>? = null,
) : IUI {

	var x by Delegates.observable(x) { _, _, _ -> redraw = true }
	var y by Delegates.observable(y) { _, _, _ -> redraw = true }

	var icon by Delegates.observable(icon) { _, _, _ -> redraw = true }

	private var redraw = true

	override fun redraw(): Boolean = redraw

	override fun update() {}
	override fun draw(canvas: IUICanvas) {
		redraw = false
		icon?.let { canvas.draw(x, y, it) }
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean {
		if (this.x != x || this.y != y) return false
		handler?.accept(clicker, type)
		return true
	}

}