package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.tools.items.clone
import me.fanhua.piggies.tools.items.enchanted
import me.fanhua.piggies.tools.items.suffix
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class UISelection constructor(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	selected: Int = -1,
	var disabled: Boolean = false,
	var changed: ((UISelection, Int) -> Unit)? = null,
) : UIGrid(x, y, width, height) {

	var selected by observable(selected) { changed?.invoke(this, it) }

	inner class Entry internal constructor(
		x: Int,
		y: Int,
		onIcon: ItemStack?,
		offIcon: ItemStack?,
		var disabled: Boolean,
		val index: Int,
	) : IBasePosUI(x, y) {

		var onIcon by observable(onIcon)
		var offIcon by observable(offIcon)

		val isSelected: Boolean get() = selected == index

		override fun draw(canvas: IUICanvas) {
			redraw = false
			(if (isSelected) onIcon else offIcon)?.let { canvas.draw(x, y, it) }
		}

		override fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean
			= if (this.x != x || this.y != y) false
			else true.apply { click(index) }

		fun enable() = apply { disabled = false }
		fun disable() = apply { disabled = true }

		fun click(index: Int) = apply { if (!disabled) this@UISelection.click(index) }
		fun select(index: Int) = apply { this@UISelection.select(index) }

	}

	fun entry(x: Int, y: Int, onIcon: ItemStack? = null, offIcon: ItemStack? = null, disabled: Boolean = false)
		= add(Entry(x, y, onIcon, offIcon, disabled, ui.size))

	fun entry(x: Int, y: Int, icon: ItemStack, disabled: Boolean = false)
		= entry(x, y,
			icon.clone().suffix("§a+").enchanted,
			icon.clone().suffix("§c-"),
			disabled)

	fun entry(x: Int, y: Int, icon: ItemStack, off: Material, disabled: Boolean = false)
		= entry(x, y,
			icon.clone(off).suffix("§a+").enchanted,
			icon.clone().suffix("§c-"),
			disabled)

	fun h(onIcon: ItemStack? = null, offIcon: ItemStack? = null, disabled: Boolean = false)
		= entry(ui.size, 0, onIcon, offIcon, disabled)
	fun h(icon: ItemStack, disabled: Boolean = false)
		= entry(ui.size, 0, icon, disabled)
	fun h(icon: ItemStack, off: Material, disabled: Boolean = false)
		= entry(ui.size, 0, icon, off, disabled)

	fun v(onIcon: ItemStack? = null, offIcon: ItemStack? = null, disabled: Boolean = false)
		= entry(0, ui.size, onIcon, offIcon, disabled)
	fun v(icon: ItemStack, disabled: Boolean = false)
		= entry(0, ui.size, icon, disabled)
	fun v(icon: ItemStack, off: Material, disabled: Boolean = false)
		= entry(0, ui.size, icon, off, disabled)

	val isSelected: Boolean get() = selected > -1 && selected < ui.size
	fun isSelected(index: Int): Boolean = selected == index

	fun click(index: Int) { if (!disabled) selected = index }
	fun select(index: Int) = apply { selected = index }
	fun first() = apply { selected = 0 }
	fun last() = apply { selected = ui.size - 1 }
	fun cancel() = apply { selected = -1 }

	fun enable() = apply { disabled = false }
	fun disable() = apply { disabled = true }

	fun onChanged(changed: (UISelection, Int) -> Unit) = apply {
		this.changed = changed
	}

}

fun IUIContainer.selection(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	selected: Int = -1,
	disabled: Boolean = false,
	builder: UISelection.() -> Unit,
) = add(UISelection(x, y, width, height, selected, disabled).apply(builder))
