package me.fanhua.piggies.coroutines.events

import me.fanhua.piggies.plugins.events.CustomEvent
import me.fanhua.piggies.plugins.events.ICancellable
import org.bukkit.event.server.PluginEvent
import org.bukkit.plugin.Plugin

class PluginCoroutineExceptionEvent(
	plugin: Plugin,
	val exception: Throwable,
) : PluginEvent(plugin), ICancellable {
	companion object: CustomEvent() { @JvmStatic override fun getHandlerList() = super.getHandlerList() }
	override fun getHandlers() = HANDLERS
	override var isEventCancelled: Boolean = false
}
