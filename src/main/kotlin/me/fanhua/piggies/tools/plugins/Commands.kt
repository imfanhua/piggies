package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun IJavaPlugin.command(command: String, executor: CommandSender.(command: Command, label: String, args: Array<String>) -> Boolean)
	= plugin.getCommand(command)!!.setExecutor(executor)

fun IJavaPlugin.commandFromPlayer(command: String, executor: Player.(command: Command, label: String, args: Array<String>) -> Boolean)
	= plugin.getCommand(command)!!.setExecutor { sender, _command, label, args ->
		(sender as? Player)?.let { executor(it, _command, label, args) } ?: false
	}

fun IJavaPlugin.commandFromOp(command: String, executor: CommandSender.(command: Command, label: String, args: Array<String>) -> Boolean)
	= plugin.getCommand(command)!!.setExecutor { sender, _command, label, args ->
		if (sender.isOp) executor(sender, _command, label, args) else false
	}
