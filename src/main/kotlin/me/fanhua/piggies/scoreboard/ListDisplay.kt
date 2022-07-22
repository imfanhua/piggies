package me.fanhua.piggies.scoreboard

import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard

class ListDisplay(scoreboard: Scoreboard, id: String, title: String, slot: DisplaySlot? = DisplaySlot.PLAYER_LIST, render: RenderType = RenderType.INTEGER): ObjectiveBoard(scoreboard, id, title, slot, render) {

	operator fun get(entry: String) = objective.getScore(entry).score
	operator fun set(entry: String, score: Int) { objective.getScore(entry).score = score }

	operator fun get(entry: OfflinePlayer) = objective.getScore(entry).score
	operator fun set(entry: OfflinePlayer, score: Int) { objective.getScore(entry).score = score }

	fun plus(entry: String, amount: Int = 1) = apply { this[entry] += amount }
	fun plus(entry: OfflinePlayer, amount: Int = 1) = apply { this[entry] += amount }

	fun minus(entry: String, amount: Int = 1) = apply { this[entry] -= amount }
	fun minus(entry: OfflinePlayer, amount: Int = 1) = apply { this[entry] -= amount }

	fun times(entry: String, amount: Int) = apply { this[entry] *= amount }
	fun times(entry: OfflinePlayer, amount: Int) = apply { this[entry] *= amount }

	fun div(entry: String, amount: Int) = apply { this[entry] /= amount }
	fun div(entry: OfflinePlayer, amount: Int) = apply { this[entry] /= amount }

	fun rem(entry: String, amount: Int) = apply { this[entry] %= amount }
	fun rem(entry: OfflinePlayer, amount: Int) = apply { this[entry] %= amount }

	fun zero(entry: String) = apply { this[entry] = 0 }
	fun zero(entry: OfflinePlayer) = apply { this[entry] = 0 }

}