package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.gui.IInventoryFactory
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

internal enum class TypedInventory(val type: InventoryType, override val size: GUISize) : IInventoryFactory {

	BOX(InventoryType.DISPENSER, GUISize(3, 3)),
	HOPPER(InventoryType.HOPPER, GUISize(5, 1)),
	;

	override fun create(holder: InventoryHolder, title: String): Inventory
		= Bukkit.createInventory(holder, type, title)

}