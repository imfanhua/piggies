package me.fanhua.piggies.tools.data.holders

import kotlinx.serialization.Serializable
import me.fanhua.piggies.tools.data.serialization.UUIDSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

@Serializable
data class PlayerHold(@Serializable(with = UUIDSerializer::class) val id: UUID) {
	val get: Player get() = orNull!!
	val orNull: Player? get() = Bukkit.getPlayer(id)
}

val Player.hold get() = PlayerHold(uniqueId)