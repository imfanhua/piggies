package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.tools.data.holders.PlayerHold
import me.fanhua.piggies.tools.data.holders.hold
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import kotlin.properties.Delegates

class UIPlayerInventory(
	val target: PlayerHold,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	var handler: (Player.(ClickType, Int) -> Unit)? = null,
) : IUI {

	companion object {
		private const val SIZE = 41
	}

	var x by Delegates.observable(x) { _, _, _ -> redraw = true }
	var y by Delegates.observable(y) { _, _, _ -> redraw = true }
	var width by Delegates.observable(width) { _, _, _ -> redraw = true }
	var height by Delegates.observable(height) { _, _, _ -> redraw = true }

	private var lastSize: GUISize? = null
	private var redraw = true

	private val cache = arrayOfNulls<ItemStack>(SIZE)

	constructor(
		target: Player,
		x: Int = 0,
		y: Int = 0,
		width: Int = 9,
		height: Int = -1,
		handler: (Player.(ClickType, Int) -> Unit)? = null,
	): this(target.hold, x, y, width, height, handler)

	override fun redraw(): Boolean = redraw

	override fun update() {
		val inv = (target.orNull ?: return).inventory
		for (i in 0 until SIZE) {
			val item = inv.getItem(i)
			if (item == cache[i]) continue
			cache[i] = item?.clone()
			redraw = true
		}
	}

	override fun draw(canvas: IUICanvas) {
		redraw = false

		val diff = canvas.diffOf(x, y, width, height)
		val size = diff.size
		lastSize = size
		val width = size.width
		var x = 0
		var y = 0

		for (i in 0 until size.size.coerceAtMost(SIZE)) {
			cache[i]?.let { diff.draw(x, y, it) }
			if (++x >= width) {
				x = 0
				y++
			}
		}
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean {
		val lastSize = lastSize ?: return false
		val calcX = x - this.x
		val calcY = y - this.y
		if (calcX < 0 || calcY < 0 || calcX > lastSize.width || calcY > lastSize.lines) return false
		val pos = calcY * lastSize.width + calcX
		if (pos >= SIZE) return false
		handler?.invoke(clicker, type, pos)
		return true
	}

}
