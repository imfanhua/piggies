package me.fanhua.piggies.tools.items

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.profile.PlayerProfile

inline fun <T: ItemMeta> Material.item(amount: Int = 1, call: T.() -> Unit) = ItemStack(this, amount).meta(call)
@Suppress("UNCHECKED_CAST")
inline fun <T: ItemMeta> ItemStack.meta(call: T.() -> Unit) = apply { itemMeta = (itemMeta as T).apply(call) }

fun OfflinePlayer.skullOf(amount: Int = 1) = Material.PLAYER_HEAD.item<SkullMeta>(amount) {
	owningPlayer = this@skullOf
}

inline fun OfflinePlayer.skullOf(amount: Int = 1, call: SkullMeta.() -> Unit) = Material.PLAYER_HEAD.item<SkullMeta>(amount) {
	owningPlayer = this@skullOf
	call()
}

fun PlayerProfile.skullOf(amount: Int = 1) = Material.PLAYER_HEAD.item<SkullMeta>(amount) {
	ownerProfile = this@skullOf
}

inline fun PlayerProfile.skullOf(amount: Int = 1, call: SkullMeta.() -> Unit) = Material.PLAYER_HEAD.item<SkullMeta>(amount) {
	ownerProfile = this@skullOf
	call()
}
