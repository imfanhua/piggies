package me.fanhua.piggies.tools.items

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.profile.PlayerProfile

val DISPLAYABLE_REPLACES = listOf(
	ITEMS.filter { it.name.contains("_WALL") }
	.mapNotNull { Material.getMaterial(it.name.replace("_WALL", ""))?.let { to -> it to to } },
	ITEMS.filter { it.name.contains("POTTED_") }
	.mapNotNull { Material.getMaterial(it.name.replace("POTTED_", ""))?.let { to -> it to to } },
	listOf(
		Material.WATER to Material.WATER_BUCKET,
		Material.LAVA to Material.LAVA_BUCKET,
		Material.BAMBOO_SAPLING to Material.BAMBOO,
	),
).flatten().toMap()

fun ItemStack.name(name: String) = meta { setDisplayName(name) }
fun ItemStack.lore(vararg lore: String) = meta { setLore(lore.toMutableList()) }

fun ItemStack.prefix(name: String) = meta {
	setDisplayName(if (hasDisplayName()) "§7[§c$name§7] $displayName" else name)
}

fun ItemStack.suffix(name: String) = meta {
	setDisplayName(if (hasDisplayName()) "$displayName §7[§c$name§7]" else name)
}

val ItemStack.hide get() = meta { hide }

val ItemStack.enchanted get() = meta {
	enchant(type == Material.CROSSBOW)
	hide
}

val ItemStack.unbreakable get() = meta { unbreakable }

fun ItemStack.skull(player: OfflinePlayer) = meta<SkullMeta> { owningPlayer = player }
fun ItemStack.skull(profile: PlayerProfile) = meta<SkullMeta> { ownerProfile = profile }

val Material.isDisplayable get() = this != Material.AIR && isItem
val Material.displayableReplace get(): Material = DISPLAYABLE_REPLACES[this] ?: this
val Material.displayableOrNull get(): Material?
	= if (this == Material.AIR) null else displayableReplace.let { if (it.isDisplayable) it else null }
