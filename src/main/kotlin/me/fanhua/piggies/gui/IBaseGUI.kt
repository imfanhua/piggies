package me.fanhua.piggies.gui

import me.fanhua.piggies.tools.void
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder

interface IBaseGUI: InventoryHolder {

	val size: GUISize

	fun whenUpdate()
	fun whenUse(event: InventoryClickEvent)
	fun whenClose(event: InventoryCloseEvent) {}

	fun open(player: Player) = player.openInventory(inventory).void()

}