package me.fanhua.piggies.plugins.events

import org.bukkit.event.Cancellable

interface ICancellable : Cancellable {

	var isEventCancelled: Boolean

	override fun isCancelled() = isEventCancelled
	override fun setCancelled(cancel: Boolean) = run { isEventCancelled = cancel }

}
