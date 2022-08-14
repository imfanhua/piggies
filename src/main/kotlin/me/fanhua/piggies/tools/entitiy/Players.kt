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

fun Player.heal() = apply {
	health = getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
}

fun Player.healFood() = apply {
	exhaustion = 0.0f
	foodLevel = 20
	saturation = 20.0f
}

fun Player.healAll() = apply {
	heal()
	healFood()
}

fun Player.resetAttributes() = apply {
	getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.let {
		it.baseValue = 20.0
		it.modifiers.toList().forEach(it::removeModifier)
	}
}

fun Player.resetInventory() = apply {
	closeInventory()
	inventory.clear()
}

fun Player.resetLevels() = apply {
	level = 0
	exp = 0.0f
}

fun Player.resetAdvancements() = apply {
	Bukkit.advancementIterator().forEachRemaining {
		val x = getAdvancementProgress(it)
		for (criteria in x.awardedCriteria) x.revokeCriteria(criteria)
	}
}

fun Player.resetBuffs() = apply { activePotionEffects.map(PotionEffect::getType).forEach(::removePotionEffect) }

fun Player.reset() = apply {
	resetCooldown()
	resetInventory()
	resetAttributes()
	healAll()
	resetLevels()
	resetAdvancements()
	resetBuffs()
}

fun Player.items(vararg items: Material) = apply {
	inventory.addItem(*items.map(::ItemStack).toTypedArray())
}

fun Player.suckFood(target: Player) = apply {
	run {
		val food =
			(20 - foodLevel).coerceAtMost(target.foodLevel)
		if (food > 0) {
			foodLevel += food
			target.foodLevel -= food
		}
	}

	run {
		val food =
			(20.0F - saturation).coerceAtMost(target.saturation)
		if (food > 0) {
			saturation += food
			target.saturation -= food
		}
	}
}

var Player.maxHp: Double
	get() = getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
	set(value) { getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = value }

fun Player.removeMaxHealth(amount: Double): Boolean = getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.let {
	if (it.value > amount) true.apply { it.baseValue -= amount }
	else false
}
