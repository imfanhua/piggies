package me.fanhua.piggies.scoreboard

import org.bukkit.Bukkit
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard

val MainScoreboard = Bukkit.getScoreboardManager().mainScoreboard
val NewScoreboard = Bukkit.getScoreboardManager().newScoreboard

fun Scoreboard.list(id: String, title: String = id, slot: DisplaySlot? = DisplaySlot.PLAYER_LIST, render: RenderType = RenderType.INTEGER)
	= ListDisplay(this, id, title, slot, render)

fun Scoreboard.board(id: String, title: String, slot: DisplaySlot? = DisplaySlot.SIDEBAR, render: RenderType = RenderType.INTEGER)
	= BoardDisplay(this, id, title, slot, render)
