package me.fanhua.piggies.players.events

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.plugins.events.CustomEvent
import me.fanhua.piggies.tools.plugins.fire
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeaveEvent private constructor(who: Player) : PlayerEvent(who) {

	class Last internal constructor(who: Player) : PlayerEvent(who) {
		companion object: CustomEvent() { @JvmStatic override fun getHandlerList() = super.getHandlerList() }
		override fun getHandlers() = HANDLERS
	}

	companion object: CustomEvent() {

		@JvmStatic override fun getHandlerList() = super.getHandlerList()

		init {
			Piggies.on(object: Listener {

				@EventHandler(priority = EventPriority.MONITOR)
				fun onPlayerQuitEvent(event: PlayerQuitEvent) = fire(event.player)

				@EventHandler(priority = EventPriority.MONITOR)
				fun onPlayerKickEvent(event: PlayerKickEvent) = fire(event.player)

			})

			Piggies.logger.info("+ #[PlayerLeaveEvent]")
		}

		fun fire(player: Player) {
			PlayerLeaveEvent(player).fire()
			Last(player).fire()
		}

	}

	override fun getHandlers() = HANDLERS

}
