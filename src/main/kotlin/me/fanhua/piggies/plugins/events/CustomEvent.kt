package me.fanhua.piggies.plugins.events

import org.bukkit.event.HandlerList

abstract class CustomEvent {
	@Suppress("PropertyName")
	protected val HANDLERS = HandlerList()
	open fun getHandlerList() = HANDLERS
}
