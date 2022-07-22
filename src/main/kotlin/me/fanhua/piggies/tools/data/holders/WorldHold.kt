package me.fanhua.piggies.tools.data.holders

import kotlinx.serialization.Serializable
import me.fanhua.piggies.tools.data.serialization.UUIDSerializer
import org.bukkit.Bukkit
import org.bukkit.World
import java.util.UUID

@Serializable
data class WorldHold(@Serializable(with = UUIDSerializer::class) val id: UUID) {
	val get: World get() = orNull!!
	val orNull: World? get() = Bukkit.getWorld(id)
}

val World.hold get() = WorldHold(uid)