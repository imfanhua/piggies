package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import org.bukkit.Bukkit

fun IJavaPlugin.disable() = Bukkit.getPluginManager().disablePlugin(plugin)

fun IJavaPlugin.disable(message: Any?) {
	logger.error(message)
	disable()
}

fun IJavaPlugin.disable(message: Any?, error: Throwable) {
	logger.error(message, error)
	disable()
}
