package me.fanhua.piggies

import me.fanhua.piggies.coroutines.PluginCoroutine
import me.fanhua.piggies.tools.ok
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStream

abstract class PiggyPlugin: IJavaPlugin, Listener {

	private var PLUGIN: JavaPlugin? = null
	override val plugin get() = PLUGIN!!

	internal var coroutine: PluginCoroutine? = null

	open class Plugin(private val instance: PiggyPlugin) : JavaPlugin() {

		override fun onEnable() {
			instance.PLUGIN = this
			on(instance)
			instance.load()
		}

		override fun onDisable() {
			ok {
				instance.coroutine?.dispose()
				instance.coroutine = null
			}
			instance.unload()
			if (instance.PLUGIN == this) instance.PLUGIN = null
		}

	}

	protected open fun load() {}
	protected open fun unload() {}

	fun getResource(path: String): InputStream? = javaClass.getResourceAsStream("/$path")

}
