package me.fanhua.piggies.tools.misc

import org.bukkit.NamespacedKey
import org.bukkit.advancement.Advancement

fun isGameplayAdvancement(key: String) = !key.startsWith("recipes/") && !key.startsWith("/root")
fun isGameplayAdvancement(key: NamespacedKey) = isGameplayAdvancement(key.key)

val Advancement.isGameplay get() = isGameplayAdvancement(key)
