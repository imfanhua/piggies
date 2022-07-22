package me.fanhua.piggies.tools.items

import me.fanhua.piggies.tools.math.rate
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import kotlin.random.Random

fun Material.item(amount: Int = 1) = ItemStack(this, amount)

inline fun Material.item(amount: Int = 1, call: ItemMeta.() -> Unit) = ItemStack(this, amount).meta(call)
fun ItemStack.amount(amount: Int) = apply { setAmount(amount) }
fun ItemStack.clone(amount: Int) = clone().apply { setAmount(amount) }

inline fun ItemStack.meta(call: ItemMeta.() -> Unit) = apply { itemMeta = itemMeta?.apply(call) }

fun <T: ItemMeta> T.name(name: String?) = apply { setDisplayName(name) }
fun <T: ItemMeta> T.lores(vararg lore: String) = apply { setLore(lore.toMutableList()) }
fun <T: ItemMeta> T.appendLores(vararg lore: String) = apply {
	setLore((getLore() ?: mutableListOf()).apply { addAll(lore) })
}
val <T: ItemMeta> T.unbreakable get() = apply { isUnbreakable = true }
val <T: ItemMeta> T.hide get() = apply { addItemFlags(*ItemFlag.values()) }

fun <T: ItemMeta> T.damage(damage: Int) = apply { (this as? Damageable)?.damage = damage }
fun ItemStack.damage(cost: Int = 1) = apply {
	var amount = amount
	if (amount < 1) return@apply
	val meta = itemMeta as? Damageable ?: return@apply
	val max = type.maxDurability
	var damage = meta.damage + cost
	while (amount > 0 || damage > 0)
		if (damage >= max) {
			amount--
			damage -= max
		} else break
	setAmount(amount)
	if (amount > 0) {
		meta.damage = damage
		itemMeta = meta
	}
}

fun ItemStack.used(cost: Int = 1) = apply { amount -= cost }

fun <T: ItemMeta> T.enchant(isCrossbow: Boolean = false) = apply {
	addEnchant(if (isCrossbow) Enchantment.CHANNELING else Enchantment.QUICK_CHARGE, 1, true)
}
fun <T: ItemMeta> T.enchant(enchant: Enchantment, level: Int = 1, set: Boolean = false) = apply {
	addEnchant(enchant, if (set) level else getEnchantLevel(enchant) + level, true)
}
fun ItemStack.enchant(enchant: Enchantment, level: Int = 1, set: Boolean = false) = meta {
	addEnchant(enchant, if (set) level else getEnchantLevel(enchant) + level, true)
}
fun ItemStack.enchants(max: Boolean = true): List<Enchantment> {
	var stream = Arrays.stream(Enchantment.values()).filter { it.canEnchantItem(this) }
	if (max) stream = stream.filter { getEnchantmentLevel(it) < it.maxLevel }
	return stream.toList()
}
fun ItemStack.addRandomEnchant(max: Boolean = true): ItemStack = apply {
	enchants(max).randomOrNull()?.let { enchant(it, 1) }
}
fun ItemStack.addRandomEnchants(times: Int, max: Boolean): ItemStack = apply {
	for (i in 0 until Random.nextInt(times) + 1) addRandomEnchant(max)
}
fun ItemStack.addRandomEnchants(rate: Double, times: Int, max: Boolean) =
	if (Random.rate(rate)) addRandomEnchants(times, max) else this