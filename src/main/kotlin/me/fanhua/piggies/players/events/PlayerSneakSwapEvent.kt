package me.fanhua.piggies.players.events

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.players.PlayerLastSneak
import me.fanhua.piggies.plugins.events.CustomEvent
import me.fanhua.piggies.plugins.events.ICancellable
import me.fanhua.piggies.plugins.events.IUsable
import me.fanhua.piggies.tools.plugins.fire
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class PlayerSneakSwapEvent(who: Player) : PlayerEvent(who), ICancellable, IUsable {

	companion object: CustomEvent() {

		@JvmStatic override fun getHandlerList() = super.getHandlerList()

		init {
			PlayerLastSneak
			Piggies.on(object: Listener {

				@EventHandler(priority = EventPriority.MONITOR)
				fun onPlayerSwapHandItemsEvent(event: PlayerSwapHandItemsEvent) {
					if (!PlayerLastSneak.within(event.player, 1000)) return
					val result = PlayerSneakSwapEvent(event.player).fire()
					if (!result.isCancelled && result.isEventUsed) event.isCancelled = true
				}

			})

			Piggies.logger.info("+ #[PlayerSneakSwapEvent]")
		}

	}

	override fun getHandlers() = HANDLERS

	override var isEventCancelled: Boolean = false
	override var isEventUsed: Boolean = false

}
