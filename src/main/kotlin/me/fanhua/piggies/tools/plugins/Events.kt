package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import me.fanhua.piggies.tools.void
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun Listener.on(plugin: IJavaPlugin) = Bukkit.getPluginManager().registerEvents(this, plugin.plugin)
fun JavaPlugin.on(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, this)
fun JavaPlugin.on(listener: Any)
		= (listener as? Listener)?.let { Bukkit.getPluginManager().registerEvents(it, this) }.void()
fun IJavaPlugin.on(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, plugin)
fun IJavaPlugin.on(listener: Any)
	= (listener as? Listener)?.let { Bukkit.getPluginManager().registerEvents(it, plugin) }.void()

fun Listener.un() = HandlerList.unregisterAll(this)
fun JavaPlugin.un(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, this)
fun JavaPlugin.un(listener: Any)
		= (listener as? Listener)?.let { HandlerList.unregisterAll(listener) }.void()
fun IJavaPlugin.un(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, plugin)
fun IJavaPlugin.un(listener: Any)
	= (listener as? Listener)?.let { HandlerList.unregisterAll(listener) }.void()

fun <E: Event> E.fire() = this.apply { Bukkit.getPluginManager().callEvent(this) }