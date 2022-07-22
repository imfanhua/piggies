package me.fanhua.piggies.coroutines.events

import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.server.PluginEvent
import org.bukkit.plugin.Plugin

class PluginCoroutineExceptionEvent(plugin: Plugin, val exception: Throwable) : PluginEvent(plugin), Cancellable {

	companion object {
		private val HANDLERS = HandlerList()
		@JvmStatic
		fun getHandlerList() = HANDLERS
	}

	override fun getHandlers() = HANDLERS

	private var isCancelled: Boolean = false

	override fun isCancelled() = isCancelled
	override fun setCancelled(cancel: Boolean) = run { isCancelled = cancel }

}