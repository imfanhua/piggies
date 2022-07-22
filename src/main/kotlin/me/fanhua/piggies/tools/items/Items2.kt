package me.fanhua.piggies.tools.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

@Suppress("UNCHECKED_CAST")
inline fun <T: ItemMeta> Material.item(amount: Int = 1, call: T.() -> Unit) = ItemStack(this, amount).meta(call)
@Suppress("UNCHECKED_CAST")
inline fun <T: ItemMeta> ItemStack.meta(call: T.() -> Unit) = apply { itemMeta = itemMeta?.let { it as T }?.apply(call) }