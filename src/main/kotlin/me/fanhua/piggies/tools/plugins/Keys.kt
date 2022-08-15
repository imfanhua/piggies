package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import me.fanhua.piggies.PiggyPlugin
import me.fanhua.piggies.plugins.PluginKey
import org.bukkit.NamespacedKey

fun IJavaPlugin.key(key: String) = NamespacedKey(plugin, key)

fun PiggyPlugin.keyed(key: String) = PluginKey(this, key)
fun PiggyPlugin.of(key: NamespacedKey) = PluginKey(this, key.key)
