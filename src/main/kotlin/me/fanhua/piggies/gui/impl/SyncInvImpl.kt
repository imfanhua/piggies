package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.gui.GUI.linesOf
import me.fanhua.piggies.gui.IBaseGUI
import me.fanhua.piggies.tools.data.holders.hold
import me.fanhua.piggies.tools.plugins.tick
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

internal class SyncInvImpl constructor(player: Player) : IBaseGUI {

	companion object {
		private val FACTORY = linesOf(5)
		private const val SIZE = 41
	}

	private val target = player.hold
	private val inventory = FACTORY.create(this, "§7> §e${player.displayName}")
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
		sync(slot, event.whoClicked)
	}

	private fun sync(slot: Int, clicker: HumanEntity) {
		Piggies.tick { ->
			val inv = (target.orNull ?: return@tick).inventory
			val item = inv.getItem(slot)?.clone()
			inv.setItem(slot, clicker.itemOnCursor)
			clicker.setItemOnCursor(item)
		}
	}

}