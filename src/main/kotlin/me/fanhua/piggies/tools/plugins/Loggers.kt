package me.fanhua.piggies.tools.plugins

import me.fanhua.piggies.IJavaPlugin
import java.util.logging.Level
import java.util.logging.Logger

val IJavaPlugin.logger get() = plugin.logger

fun Logger.title(message: Any?) = info("= [$message] =")
fun Logger.info(message: Any?) = info("$message")

fun Logger.error(message: Any?) = severe("$message")
fun Logger.error(error: Throwable) = log(Level.SEVERE, "ERROR", error)
fun Logger.error(message: Any?, error: Throwable) = log(Level.SEVERE, "$message", error)