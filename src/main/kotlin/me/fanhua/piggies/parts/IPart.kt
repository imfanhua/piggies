package me.fanhua.piggies.parts

import org.bukkit.entity.Player

interface IPart<P> {
	operator fun get(player: Player): P
}
