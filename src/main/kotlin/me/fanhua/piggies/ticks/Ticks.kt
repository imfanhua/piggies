package me.fanhua.piggies.ticks

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.ticks.events.ServerTickEvent
import me.fanhua.piggies.tools.plugins.fire
import me.fanhua.piggies.tools.plugins.tick

object Ticks {

	var tick: Int = 0
		private set

	init {
		Piggies.tick(::tick)
	}

	private fun tick() {
		ServerTickEvent(++tick).fire()
	}

}