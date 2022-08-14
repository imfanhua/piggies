package me.fanhua.piggies.gui.image

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.gui.ui.IUICanvas
import me.fanhua.piggies.tools.items.AIR
import org.bukkit.inventory.ItemStack

class GUIImageBuilder(val width: Int, val lines: Int): IUICanvas {

	override val size = GUISize(width, lines)

	private val canvas: Array<Array<ItemStack?>> = Array(lines) { arrayOfNulls(width) }

	private var pattern: Array<out String>? = null
	private val icons: MutableMap<Char, ItemStack> = HashMap()

	override fun draw(x: Int, y: Int, item: ItemStack?) {
		canvas[x][y] = item
	}

	fun icon(input: Char, output: ItemStack) = apply { icons[input] = output }
	fun air(input: Char) = icon(input, AIR)

	inline fun place(vararg pattern: String, builder: GUIImageBuilder.() -> Unit) = apply {
		place(*pattern)
		builder()
		place()
	}

	fun place(vararg pattern: String) = apply {
		place()
		this.pattern = pattern
	}

	fun place() = apply {
		pattern?.let { pattern ->
			val patternLines = pattern.size
			val patternWidth = pattern.getOrNull(0)?.length ?: 0
			if (patternLines != 0 && patternWidth != 0) {
				for (diffY in 0 until lines) {
					val items = pattern[diffY]
					for (diffX in 0 until width)
						icons[items[diffX]]?.let { draw(diffX, diffY, it) }
				}
			}
		}

		pattern = null
		icons.clear()
	}

	internal fun build(builder: GUIImageBuilder.() -> Unit): GUIImage = let {
		builder()
		place()
		GUIImage(canvas)
	}

}