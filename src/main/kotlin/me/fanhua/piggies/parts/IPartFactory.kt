package me.fanhua.piggies.parts

import org.bukkit.entity.Player

interface IPartFactory<P> {
	fun load(player: Player): P
	fun save(player: Player, part: P)
}