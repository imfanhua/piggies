package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.function.Consumer

// Main
fun JavaPlugin.main(run: Runnable) {
	if (Bukkit.isPrimaryThread()) run.run() else Bukkit.getScheduler().runTask(this, run)
}

fun IJavaPlugin.main(run: Runnable) {
	if (Bukkit.isPrimaryThread()) run.run() else Bukkit.getScheduler().runTask(plugin, run)
}

// Ticks
fun JavaPlugin.tick(task: Runnable) = Bukkit.getScheduler().runTask(this, task)
fun IJavaPlugin.tick(task: Runnable) = Bukkit.getScheduler().runTask(plugin, task)

fun JavaPlugin.tick(delay: Long, task: Runnable) = Bukkit.getScheduler().runTaskLater(this, task, delay)
fun IJavaPlugin.tick(delay: Long, task: Runnable) = Bukkit.getScheduler().runTaskLater(plugin, task, delay)

// Ticks (Consumer)
fun JavaPlugin.tick(task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskLater(this, task, 0)
fun IJavaPlugin.tick(task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskLater(plugin, task, 0)

fun JavaPlugin.tick(delay: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskLater(this, task, delay)
fun IJavaPlugin.tick(delay: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskLater(plugin, task, delay)

// Tasks
fun JavaPlugin.task(task: Runnable) = Bukkit.getScheduler().runTaskTimer(this, task, 0, 0)
fun IJavaPlugin.task(task: Runnable) = Bukkit.getScheduler().runTaskTimer(plugin, task, 0, 0)

fun JavaPlugin.task(period: Long, task: Runnable) = Bukkit.getScheduler().runTaskTimer(this, task, period, period)
fun IJavaPlugin.task(period: Long, task: Runnable) = Bukkit.getScheduler().runTaskTimer(plugin, task, period, period)

fun JavaPlugin.task(delay: Long, period: Long, task: Runnable) = Bukkit.getScheduler().runTaskTimer(this, task, delay, period)
fun IJavaPlugin.task(delay: Long, period: Long, task: Runnable) = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)

// Tasks (Consumer)
fun JavaPlugin.task(task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(this, task, 0, 0)
fun IJavaPlugin.task(task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(plugin, task, 0, 0)

fun JavaPlugin.task(period: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(this, task, period, period)
fun IJavaPlugin.task(period: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(plugin, task, period, period)

fun JavaPlugin.task(delay: Long, period: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(this, task, delay, period)
fun IJavaPlugin.task(delay: Long, period: Long, task: Consumer<BukkitTask>) = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)