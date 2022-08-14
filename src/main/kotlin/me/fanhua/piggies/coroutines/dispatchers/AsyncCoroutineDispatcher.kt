package me.fanhua.piggies.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

internal class AsyncCoroutineDispatcher(val plugin: JavaPlugin) : CoroutineDispatcher() {

	override fun isDispatchNeeded(context: CoroutineContext): Boolean = Bukkit.isPrimaryThread()

	override fun dispatch(context: CoroutineContext, block: Runnable) {
		if (plugin.isEnabled) Bukkit.getScheduler().runTaskAsynchronously(plugin, block)
	}

}
