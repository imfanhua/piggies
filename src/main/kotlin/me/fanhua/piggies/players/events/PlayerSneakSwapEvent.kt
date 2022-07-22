package me.fanhua.piggies.players.events

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.players.PlayerLastSneak
import me.fanhua.piggies.tools.plugins.fire
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.entity.Player
import org.bukkit.event.*
import org.bukkit.event.player.*

class PlayerSneakSwapEvent(who: Player) : PlayerEvent(who), Cancellable {

	companion object {
		private val HANDLERS = HandlerList()
		@JvmStatic
		fun getHandlerList() = HANDLERS

		init {
			PlayerLastSneak
			Piggies.on(object: Listener {

				@EventHandler(priority = EventPriority.MONITOR)
				fun onPlayerSwapHandItemsEvent(event: PlayerSwapHandItemsEvent) {
					if (!PlayerLastSneak.within(event.player, 1000)) return
					val result = PlayerSneakSwapEvent(event.player).fire()
					if (!result.isCancelled && result.isUsed) event.isCancelled = true
				}

			})
		}

	}

	override fun getHandlers() = HANDLERS

	private var isCancelled: Boolean = false
	var isUsed: Boolean = false

	override fun isCancelled() = isCancelled
	override fun setCancelled(cancel: Boolean) = run { isCancelled = cancel }

	fun use() = apply { isUsed = true }

}