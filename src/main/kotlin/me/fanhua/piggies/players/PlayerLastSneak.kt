package me.fanhua.piggies.players

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.parts.Parts
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

private data class LastSneak(var last: Long? = null)
private val LastSneakPart = Parts.temp(::LastSneak)

object PlayerLastSneak {

	init {
		LastSneakPart
		Piggies.on(object: Listener {

			@EventHandler(priority = EventPriority.MONITOR)
			fun onPlayerToggleSneakEvent(event: PlayerToggleSneakEvent)
				= run { if (event.isSneaking) LastSneakPart[event.player].last = System.currentTimeMillis() }

		})

		Piggies.logger.info("+ #[PlayerLastSneak]")
	}

	fun within(player: Player, time: Long) = get(player)?.let { System.currentTimeMillis() - it < time } == true

	operator fun get(player: Player): Long? = LastSneakPart[player].last

}
