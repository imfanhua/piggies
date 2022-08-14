package me.fanhua.piggies.plugins

import me.fanhua.piggies.PiggyPlugin
import me.fanhua.piggies.tools.void

private val PiggyPlugin.mustLasts: MutableList<() -> Unit> get()
	= lasts ?: let {
		val plugin = plugin
		if (!plugin.isEnabled) throw RuntimeException("Plugin (\"${plugin.name}\") not enabled!")
		mutableListOf<() -> Unit>().apply { lasts = this }
	}

fun PiggyPlugin.last(block: () -> Unit): Unit = mustLasts.add(block).void()
