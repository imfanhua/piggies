package me.fanhua.piggies.tools.entitiy

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

val Player.isInGame get()
	= when (gameMode) {
		GameMode.SURVIVAL, GameMode.ADVENTURE -> true
		else -> false
	}

fun Player.reset() = apply {
	closeInventory()
	inventory.clear()
	exhaustion = 0.0f
	foodLevel = 20
	saturation = 20.0f
	level = 0
	exp = 0.0f
	health = getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
	Bukkit.advancementIterator().forEachRemaining {
		val x = getAdvancementProgress(it)
		for (criteria in x.awardedCriteria) x.revokeCriteria(criteria)
	}
	activePotionEffects.map(PotionEffect::getType).forEach(::removePotionEffect)
}

fun Player.items(vararg items: Material) = apply {
	inventory.addItem(*items.map(::ItemStack).toTypedArray())
}