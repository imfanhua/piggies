package me.fanhua.piggies.tools.items

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.remove(target: Material, needs: Int): Boolean
	= remove(all(target), needs)

fun Inventory.remove(target: ItemStack, needs: Int): Boolean
	= remove(all(target), needs)

fun Inventory.remove(all: Map<Int, ItemStack>, needs: Int): Boolean {
	if (all.values.sumOf { it.amount } < needs) return false
	var amount = needs
	for (item in all.values) {
		val number = item.amount.coerceAtMost(amount)
		item.amount -= number
		amount -= number
		if (amount < 1) return true
	}
	return true
}

fun Inventory.removeAll(target: Material, max: Int): Int
	= removeAll(all(target), max)

fun Inventory.removeAll(target: ItemStack, max: Int): Int
	= removeAll(all(target), max)

fun Inventory.removeAll(all: Map<Int, ItemStack>, max: Int): Int {
	var amount = max
	for (item in all.values) {
		val number = item.amount.coerceAtMost(amount)
		item.amount -= number
		amount -= number
		if (amount < 1) return max
	}
	return max - amount
}

fun Inventory.removeAll(target: Material): Int
	= removeAll(all(target))

fun Inventory.removeAll(target: ItemStack): Int
	= removeAll(all(target))

fun Inventory.removeAll(all: Map<Int, ItemStack>): Int
	 = all.values.sumOf {
		it.amount.apply { it.amount = 0 }
	}

fun Inventory.remove1Slot(item: ItemStack): Boolean = indexOf(item).let {
	if (it == -1) false else true.apply { setItem(it, null) }
}

fun Player.give(vararg items: ItemStack): Unit = inventory.give(location, *items)

fun Inventory.give(drop: Location, vararg items: ItemStack): Unit = addItem(*items.map(ItemStack::clone).toTypedArray()).let {
	if (it.isNotEmpty()) {
		val world = drop.world!!
		for (extra in it.values)
			world.dropItem(drop, extra.clone()).pickupDelay = 0
	}
}

fun ItemStack.addTo(player: Player): Map<Int, ItemStack> = addTo(player.inventory)
fun ItemStack.addTo(inventory: Inventory): Map<Int, ItemStack> = inventory.addItem(this)
fun Array<ItemStack>.addTo(player: Player): Map<Int, ItemStack> = addTo(player.inventory)
fun Array<ItemStack>.addTo(inventory: Inventory): Map<Int, ItemStack> = inventory.addItem(*this.map(ItemStack::clone).toTypedArray())
fun Collection<ItemStack>.addTo(player: Player): Map<Int, ItemStack> = addTo(player.inventory)
fun Collection<ItemStack>.addTo(inventory: Inventory): Map<Int, ItemStack> = inventory.addItem(*this.map(ItemStack::clone).toTypedArray())
fun Map<Int, ItemStack>.addTo(player: Player): Map<Int, ItemStack> = addTo(player.inventory)
fun Map<Int, ItemStack>.addTo(inventory: Inventory): Map<Int, ItemStack>
		= if (isEmpty()) emptyMap() else inventory.addItem(*values.map(ItemStack::clone).toTypedArray())

fun <T: Entity> Map<Int, ItemStack>.dropOn(entity: T) = entity.apply { dropOn(entity.location) }
fun Map<Int, ItemStack>.dropOn(location: Location) = location.apply {
	if (isNotEmpty()) {
		val world = location.world!!
		for (extra in values)
			world.dropItem(location, extra.clone()).pickupDelay = 0
	}
}
