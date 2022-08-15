package me.fanhua.piggies.scoreboard

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team

val Team.onlinePlayers get() = Bukkit.getOnlinePlayers().filter(::isIn)
fun Team.isIn(player: OfflinePlayer) = hasPlayer(player)
fun Team.join(player: OfflinePlayer) = apply { addPlayer(player) }
fun Team.leave(player: OfflinePlayer) = apply { removePlayer(player) }
fun OfflinePlayer.isIn(team: Team) = team.isIn(this)
fun <T: OfflinePlayer> T.join(team: Team) = apply { team.join(this) }
fun <T: OfflinePlayer> T.leave(team: Team) = apply { team.leave(this) }
