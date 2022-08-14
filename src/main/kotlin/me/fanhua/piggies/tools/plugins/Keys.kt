package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import me.fanhua.piggies.PiggyPlugin
import me.fanhua.piggies.plugins.PluginKey
import org.bukkit.NamespacedKey

fun IJavaPlugin.key(key: String) = NamespacedKey(plugin, key)

val NamespacedKey.path get() = toString().let { it.substring(it.indexOf(':') + 1) }

fun PiggyPlugin.keyed(key: String) = PluginKey(this, key)
fun PiggyPlugin.of(key: NamespacedKey) = PluginKey(this, key.path)
