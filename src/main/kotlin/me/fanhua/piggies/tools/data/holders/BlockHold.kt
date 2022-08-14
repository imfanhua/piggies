package me.fanhua.piggies.tools.data.holders

import kotlinx.serialization.Serializable
import me.fanhua.piggies.tools.data.serialization.UUIDSerializer
import me.fanhua.piggies.tools.math.Pos3I
import org.bukkit.Bukkit
import org.bukkit.block.Block
import java.util.*

@Serializable
data class BlockHold(
	@Serializable(with = UUIDSerializer::class) val id: UUID,
	private val pos: Pos3I
) {
	val get: Block get() = orNull!!
	val orNull: Block? get() = Bukkit.getWorld(id)?.let(pos::get)
}

val Block.hold get() = BlockHold(world.uid, Pos3I(location))
