package me.fanhua.piggies.parts.impl

import me.fanhua.piggies.parts.IPartFactory
import org.bukkit.entity.Player

class TempPartFactory<P>(val factory: () -> P) : IPartFactory<P> {

	override fun load(player: Player): P = factory()
	override fun save(player: Player, part: P) {}

}