package me.fanhua.piggies.gui

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

interface IInventoryFactory {
	val size: GUISize
	fun create(holder: InventoryHolder, title: String): Inventory
}
