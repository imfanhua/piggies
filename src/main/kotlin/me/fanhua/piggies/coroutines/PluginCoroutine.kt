package me.fanhua.piggies.coroutines

import kotlinx.coroutines.*
import me.fanhua.piggies.coroutines.dispatchers.AsyncCoroutineDispatcher
import me.fanhua.piggies.coroutines.dispatchers.MinecraftCoroutineDispatcher
import me.fanhua.piggies.coroutines.events.PluginCoroutineExceptionEvent
import me.fanhua.piggies.tools.plugins.error
import me.fanhua.piggies.tools.plugins.fire
import me.fanhua.piggies.tools.plugins.main
import org.bukkit.plugin.java.JavaPlugin

internal class PluginCoroutine(val plugin: JavaPlugin) {

	val sync = MinecraftCoroutineDispatcher(plugin)
	val async = AsyncCoroutineDispatcher(plugin)

	val scope: CoroutineScope
		= CoroutineScope(CoroutineExceptionHandler { _, exception ->
			if (plugin.isEnabled)
				plugin.main {
					if (!PluginCoroutineExceptionEvent(plugin, exception).fire().isCancelled)
						if (exception !is CancellationException)
							plugin.logger.error("Uncaught coroutine exception:", exception)
				}
		}) + SupervisorJob() + sync

	fun dispose() {
		scope.coroutineContext.cancelChildren()
		scope.cancel()
	}

}
