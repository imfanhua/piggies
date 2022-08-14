package me.fanhua.piggies.coroutines

import kotlinx.coroutines.*
import me.fanhua.piggies.PiggyPlugin
import kotlin.coroutines.CoroutineContext

private val PiggyPlugin.mustCoroutine: PluginCoroutine get()
	= coroutine ?: let {
		val plugin = plugin
		if (!plugin.isEnabled) throw RuntimeException("Plugin (\"${plugin.name}\") not enabled!")
		PluginCoroutine(plugin).apply { coroutine = this }
	}

val PiggyPlugin.sync: CoroutineContext get() = mustCoroutine.sync

val PiggyPlugin.async: CoroutineContext get() = mustCoroutine.async

fun PiggyPlugin.launch(
	context: CoroutineContext? = null,
	start: CoroutineStart = CoroutineStart.DEFAULT,
	block: suspend CoroutineScope.() -> Unit
): Job {
	val coroutine = mustCoroutine
	val scope = coroutine.scope
	return if (!scope.isActive) Job() else scope.launch(context ?: coroutine.sync, start, block)
}

suspend fun <T> PiggyPlugin.sync(block: suspend CoroutineScope.() -> T): T = withContext(sync, block)

suspend fun <T> PiggyPlugin.async(block: suspend CoroutineScope.() -> T): T = withContext(async, block)
