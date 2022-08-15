package me.fanhua.piggies.tools.items

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.profile.PlayerProfile

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
