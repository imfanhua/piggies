package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUIImage
import me.fanhua.piggies.tools.data.holders.PlayerHold
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

fun IUIContainer.image(
	x: Int = 0,
	y: Int = 0,
	image: GUIImage? = null,
) = add(UIImage(x, y, image))

fun IUIContainer.button(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	handler: (Player.(ClickType) -> Unit)? = null,
) = add(UIButton(x, y, icon, handler))

fun IUIContainer.grid(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) = add(UIGrid(x, y, width, height))

inline fun IUIContainer.grid(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	call: UIGrid.() -> Unit
) = grid(x, y, width, height).apply(call)

fun IUIContainer.inv(
	target: PlayerHold,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	handler: (Player.(ClickType, Int) -> Unit)? = null,
) = add(UIPlayerInventory(target, x, y, width, height, handler))

fun IUIContainer.inv(
	target: Player,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	handler: (Player.(ClickType, Int) -> Unit)? = null,
) = add(UIPlayerInventory(target, x, y, width, height, handler))
