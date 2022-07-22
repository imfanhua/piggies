package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.gui.ui.IUICanvas
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

internal class InvUICanvasImpl(
	override val size: GUISize,
	private val inventory: Inventory,
) : IUICanvas {

	override fun draw(x: Int, y: Int, item: ItemStack?) =
		inventory.setItem(size[x, y], item)

}
