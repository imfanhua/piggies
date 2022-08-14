package me.fanhua.piggies.tools.entitiy

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

const val KILL_DAMAGE = 100000000.0

fun <T: LivingEntity> T.kill() = apply {
	noDamageTicks = 0
	damage(KILL_DAMAGE)
}

fun <T: LivingEntity> T.kill(source: Entity?) = apply {
	noDamageTicks = 0
	damage(KILL_DAMAGE, source)
}

fun <T: LivingEntity> T.explosion(power: Float, fire: Boolean, breakBlocks: Boolean, kill: Boolean = false) = apply {
	noDamageTicks = 0
	world.createExplosion(location.clone().add(0.0, 0.1, 0.0), power, fire, breakBlocks)
	if (kill) damage(KILL_DAMAGE)
}

fun <T: LivingEntity> T.explosion(power: Float, fire: Boolean, breakBlocks: Boolean, source: Entity?, kill: Boolean = false) = apply {
	if (kill || this != source) noDamageTicks = 0
	world.createExplosion(location.clone().add(0.0, 0.1, 0.0), power, fire, breakBlocks, source)
	if (kill) damage(KILL_DAMAGE)
}

fun <T: LivingEntity> T.suck(target: LivingEntity) = apply {
	val hp = (getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value - health).coerceAtMost(target.health)
	if (hp > 0) {
		health += hp
		target.noDamageTicks = 0
		val targetHealth = target.health - hp
		target.damage(hp, this)
		target.health = targetHealth
	}
}
