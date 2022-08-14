package me.fanhua.piggies.tools.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.prefix(name: String) = meta {
	setDisplayName(if (hasDisplayName()) "§7[§c$name§7] $displayName" else name)
}

fun ItemStack.suffix(name: String) = meta {
	setDisplayName(if (hasDisplayName()) "$displayName §7[§c$name§7]" else name)
}

val ItemStack.enchanted get() = meta {
	enchant(type == Material.CROSSBOW)
	hide
}

val ItemStack.unbreakable get() = meta { unbreakable }
