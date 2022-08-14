package me.fanhua.piggies.plugins.events

import org.bukkit.event.Cancellable

fun <T: Cancellable> T.cancel(): T = apply { isCancelled = true }
fun <T: IUsable> T.use(): T = apply { isEventUsed = true }
