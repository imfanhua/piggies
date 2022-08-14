package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.gui.GUI
import me.fanhua.piggies.gui.IBaseGUI
import me.fanhua.piggies.tools.data.holders.hold
import me.fanhua.piggies.tools.items.give
import me.fanhua.piggies.tools.plugins.tick
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

internal class SyncInvImpl(player: Player) : IBaseGUI {

	companion object {
		private val FACTORY = GUI.PLAYER
		private const val SIZE = 41
	}

	private val target = player.hold
	private val inventory = FACTORY.create(this, "§7> §0${player.displayName}")
	override fun getInventory(): Inventory = inventory

	override val size get() = FACTORY.size

	override fun whenUpdate() {
		val target = target.orNull
		if (target == null) {
			for (x in inventory.viewers) x.closeInventory()
			return
		}
		val inv = target.inventory
		for (i in 0 until SIZE) {
			val item = inv.getItem(i)
			if (item == inventory.getItem(i)) continue
			inventory.setItem(i, item?.clone())
		}
	}

	override fun whenUse(event: InventoryClickEvent) {
		if (event.clickedInventory !== inventory) {
			event.isCancelled = event.click != ClickType.LEFT
			return
		}
		val slot = event.slot
		if (slot >= SIZE) return
		(event.whoClicked as? Player)?.let { sync(slot, it, event.click) }
	}

	private fun sync(slot: Int, clicker: Player, type: ClickType) {
		Piggies.tick { ->
			val inv = (target.orNull ?: return@tick).inventory
			val item = inv.getItem(slot)?.clone()
			if (type.isShiftClick) {
				inv.setItem(slot, null)
				item?.let { clicker.give(it) }
			} else {
				inv.setItem(slot, clicker.itemOnCursor)
				clicker.setItemOnCursor(item)
			}
		}
	}

}
