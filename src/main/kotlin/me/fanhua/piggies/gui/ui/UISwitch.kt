package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.tools.items.clone
import me.fanhua.piggies.tools.items.enchanted
import me.fanhua.piggies.tools.items.suffix
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class UISwitch(
	x: Int,
	y: Int,
	iconOn: ItemStack? = null,
	iconOff: ItemStack? = null,
	on: Boolean = false,
	var disabled: Boolean = false,
	var handler: ((Player, ActionType, UISwitch) -> Boolean)? = null,
	var changed: ((UISwitch, Boolean) -> Unit)? = null,
) : IBasePosUI(x, y) {

	var iconOn by observable(iconOn)
	var iconOff by observable(iconOff)

	var on by observable(on) { changed?.invoke(this, it) }

	override fun draw(canvas: IUICanvas) {
		redraw = false
		(if (on) iconOn else iconOff)?.let { canvas.draw(x, y, it) }
	}

	override fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean
		= if (this.x != x || this.y != y) false
		else true.apply {
			if (!disabled && handler?.invoke(clicker, type, this@UISwitch) != false) on = !on
		}

	fun enable() = apply { disabled = false }
	fun disable() = apply { disabled = true }

	fun on() = apply { on = true }
	fun off() = apply { on = false }
	fun toggle() = apply { on = !on }

	fun onUse(handler: (Player, ActionType, UISwitch) -> Boolean) = apply {
		this.handler = handler
	}

}

fun IUIContainer.switch(
	x: Int,
	y: Int,
	iconOn: ItemStack? = null,
	iconOff: ItemStack? = null,
	on: Boolean = false,
	disabled: Boolean = false,
	handler: ((Player, ActionType, UISwitch) -> Boolean)? = null,
	changed: ((UISwitch, Boolean) -> Unit)? = null,
) = add(UISwitch(x, y, iconOn, iconOff, on, disabled, handler, changed))

fun IUIContainer.switch(
	x: Int,
	y: Int,
	icon: ItemStack,
	on: Boolean = false,
	disabled: Boolean = false,
	handler: ((Player, ActionType, UISwitch) -> Boolean)? = null,
	changed: ((UISwitch, Boolean) -> Unit)? = null,
) = switch(x, y,
	icon.clone().suffix("§a+").enchanted,
	icon.clone().suffix("§c-"),
	on, disabled, handler, changed)

fun IUIContainer.switch(
	x: Int,
	y: Int,
	icon: ItemStack,
	on: Boolean = false,
	off: Material,
	disabled: Boolean = false,
	handler: ((Player, ActionType, UISwitch) -> Boolean)? = null,
	changed: ((UISwitch, Boolean) -> Unit)? = null,
) = switch(x, y,
	icon.clone(off).suffix("§a+").enchanted,
	icon.clone().suffix("§c-"),
	on, disabled, handler, changed)
