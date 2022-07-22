package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.gui.IInventoryFactory
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

internal class ChestInventoryFactory(lines: Int) : IInventoryFactory {

	override val size = GUISize(9, lines)

	fun size(): GUISize {
		return size
	}

	override fun create(holder: InventoryHolder, title: String): Inventory
		= Bukkit.createInventory(holder, size.size, title)

}
