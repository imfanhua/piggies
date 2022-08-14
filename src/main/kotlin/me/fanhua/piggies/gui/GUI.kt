package me.fanhua.piggies.gui

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.gui.image.GUIImage
import me.fanhua.piggies.gui.image.GUIImageBuilder
import me.fanhua.piggies.gui.impl.ChestInventoryFactory
import me.fanhua.piggies.gui.impl.SyncInvImpl
import me.fanhua.piggies.gui.impl.TypedInventory
import me.fanhua.piggies.ticks.events.ServerTickEvent
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import me.fanhua.piggies.tools.void
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory

object GUI {

	init {
		Piggies.on(object: Listener {

			@EventHandler(priority = EventPriority.LOW)
			fun onInventoryClickEvent(event: InventoryClickEvent) {
				val top = event.view.topInventory
				val gui = top.holder as? IBaseGUI ?: return
				val inv = event.clickedInventory
				if (inv != top) {
					event.isCancelled = event.click != ClickType.LEFT
					return
				}
				if (inv.holder != gui) return
				event.isCancelled = true
				gui.whenUse(event)
			}

			@EventHandler
			fun onInventoryDragEvent(event: InventoryDragEvent)
				= run { if (event.view.topInventory.holder is IBaseGUI) event.isCancelled = true }

			@EventHandler(priority = EventPriority.LOW)
			fun onInventoryCloseEvent(event: InventoryCloseEvent) =
				GUI[event.inventory]?.whenClose(event).void()

			@EventHandler(priority = EventPriority.LOW)
			fun onServerTickEvent(event: ServerTickEvent) {
				for (player in Bukkit.getOnlinePlayers())
					GUI[player]?.whenUpdate()
			}

		})

		Piggies.logger.info("+ #[GUI]")
	}

	operator fun get(inventory: Inventory): IBaseGUI? = inventory.holder as? IBaseGUI
	operator fun get(player: Player): IBaseGUI? = player.openInventory.topInventory.holder as? IBaseGUI

	fun imageOf(width: Int, lines: Int, builder: GUIImageBuilder.() -> Unit): GUIImage
		= GUIImageBuilder(width, lines).build(builder)

	fun imageOf(vararg pattern: String, builder: GUIImageBuilder.() -> Unit): GUIImage
		= GUIImageBuilder(pattern[0].length, pattern.size).place(*pattern).build(builder)

	val BOX: IInventoryFactory = TypedInventory.BOX
	val HOPPER: IInventoryFactory = TypedInventory.HOPPER
	val MIN: IInventoryFactory = ChestInventoryFactory(1)
	val NORMAL: IInventoryFactory = ChestInventoryFactory(3)
	val PLAYER: IInventoryFactory = ChestInventoryFactory(5)
	val MAX: IInventoryFactory = ChestInventoryFactory(6)

	fun sizeOf(size: Int): IInventoryFactory = ChestInventoryFactory(size / 9)

	fun linesOf(lines: Int): IInventoryFactory = ChestInventoryFactory(lines)

	fun syncOf(target: Player): IBaseGUI = SyncInvImpl(target)

}
