package me.fanhua.piggies.plugins

import me.fanhua.piggies.PiggyPlugin
import me.fanhua.piggies.tools.plugins.key

data class PluginKey(val plugin: PiggyPlugin, val path: String) {
	val key by lazy { plugin.key(path) }
}
