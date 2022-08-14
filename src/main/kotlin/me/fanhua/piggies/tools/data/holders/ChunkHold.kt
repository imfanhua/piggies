package me.fanhua.piggies.tools.data.holders

import kotlinx.serialization.Serializable
import me.fanhua.piggies.tools.data.serialization.UUIDSerializer
import org.bukkit.Bukkit
import org.bukkit.Chunk
import java.util.*

@Serializable
data class ChunkHold(
	@Serializable(with = UUIDSerializer::class) val id: UUID,
	val x: Int,
	val z: Int,
) {
	val get: Chunk get() = orNull!!
	val orNull: Chunk? get() = Bukkit.getWorld(id)?.getChunkAt(x, z)
}

val Chunk.hold get() = ChunkHold(world.uid, x, z)
