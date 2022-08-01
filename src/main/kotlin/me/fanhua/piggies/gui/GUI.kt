package me.fanhua.piggies.gui

import me.fanhua.piggies.Piggies
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
import org.bukkit.inventory.ItemStack

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
				(event.inventory.holder as? IBaseGUI)?.whenClose(event).void()

			@EventHandler(priority = EventPriority.LOW)
			fun onServerTickEvent(event: ServerTickEvent) {
				for (player in Bukkit.getOnlinePlayers())
					(player.openInventory.topInventory.holder as? IBaseGUI)?.whenUpdate()
			}

		})

		Piggies.logger.info("+ #[GUI]")
	}

	class PatternBuilder internal constructor(private val lines: Array<out String>) {

		private val items: MutableMap<Char, ItemStack> = HashMap()

		fun icon(input: Char, output: ItemStack): PatternBuilder {
			items[input] = output
			return this
		}

		internal fun done(): GUIImage {
			val icons: Array<Array<ItemStack?>?> = arrayOfNulls(lines.size)
			for (i in lines.indices) {
				val line = lines[i].toCharArray()
				val list = arrayOfNulls<ItemStack>(line.size)
				icons[i] = list
				for (c in line.indices) list[c] = items[line[c]]
			}
			@Suppress("UNCHECKED_CAST")
			return GUIImage(icons as Array<Array<ItemStack?>>)
		}

	}

	fun imageOf(vararg lines: String, builder: PatternBuilder.() -> Unit): GUIImage
		= PatternBuilder(lines).apply(builder).done()

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