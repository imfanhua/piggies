package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.gui.GUI
import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.gui.contains
import me.fanhua.piggies.tools.data.holders.PlayerHold
import me.fanhua.piggies.tools.data.holders.hold
import me.fanhua.piggies.tools.plugins.tick
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class UIPlayerInventory(
	val target: PlayerHold,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	var handler: ((Player, ActionType, Int) -> Unit)? = null,
) : IBaseSizedUI(x, y, width, height) {

	companion object {
		private const val SIZE = 41
	}

	private val cache = arrayOfNulls<ItemStack>(SIZE)

	constructor(
		target: Player,
		x: Int = 0,
		y: Int = 0,
		width: Int = -1,
		height: Int = -1,
		handler: (Player.(ActionType, Int) -> Unit)? = null,
	): this(target.hold, x, y, width, height, handler)

	override fun update() {
		val inv = (target.orNull ?: return).inventory
		for (i in 0 until SIZE) {
			val item = inv.getItem(i)
			if (item == cache[i]) continue
			cache[i] = item?.clone()
			redraw = true
		}
	}

	override fun whenDraw(canvas: IUICanvas) {
		val size = canvas.size
		val width = size.width
		var x = 0
		var y = 0

		for (i in 0 until size.size.coerceAtMost(SIZE)) {
			cache[i]?.let { canvas.draw(x, y, it) }
			if (++x >= width) {
				x = 0
				y++
			}
		}
	}

	override fun whenUse(clicker: Player, type: ActionType, x: Int, y: Int, size: GUISize): Boolean
		= size[x, y].let {
			if (it >= SIZE) false
			else true.apply { if (handler != null) used(clicker.hold, type, it) }
		}

	private fun used(clicker: PlayerHold, type: ActionType, slot: Int) = Piggies.tick { ->
		clicker.orNull?.let {
			if (GUI[it].contains(this)) handler?.invoke(it, type, slot)
		}
	}

}

fun IUIContainer.inv(
	target: PlayerHold,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	handler: ((Player, ActionType, Int) -> Unit)? = null,
) = add(UIPlayerInventory(target, x, y, width, height, handler))

fun IUIContainer.inv(
	target: Player,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	handler: ((Player, ActionType, Int) -> Unit)? = null,
) = add(UIPlayerInventory(target, x, y, width, height, handler))
