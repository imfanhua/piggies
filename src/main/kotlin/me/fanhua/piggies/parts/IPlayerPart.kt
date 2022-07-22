package me.fanhua.piggies.parts

import org.bukkit.entity.Player

interface IPlayerPart {
	fun load(player: Player)
	fun unload(player: Player)
}