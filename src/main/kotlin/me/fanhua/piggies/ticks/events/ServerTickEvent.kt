package me.fanhua.piggies.ticks.events

import me.fanhua.piggies.plugins.events.CustomEvent
import me.fanhua.piggies.ticks.Ticks
import org.bukkit.event.server.ServerEvent

class ServerTickEvent(val tick: Int) : ServerEvent() {

	companion object: CustomEvent() {

		@JvmStatic override fun getHandlerList() = super.getHandlerList()

		init {
			Ticks
		}

	}

	override fun getHandlers() = HANDLERS

}
