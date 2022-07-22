package me.fanhua.piggies.ticks.events

import me.fanhua.piggies.ticks.Ticks
import org.bukkit.event.HandlerList
import org.bukkit.event.server.ServerEvent

class ServerTickEvent(val tick: Int) : ServerEvent() {

	companion object {
		@JvmField
		val HANDLERS = HandlerList()
		@JvmStatic
		fun getHandlerList() = HANDLERS

		init {
			Ticks
		}
	}

	override fun getHandlers() = HANDLERS

}