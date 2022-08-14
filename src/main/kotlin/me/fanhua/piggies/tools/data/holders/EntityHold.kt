package me.fanhua.piggies.tools.data.holders

import kotlinx.serialization.Serializable
import me.fanhua.piggies.tools.data.serialization.UUIDSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import java.util.*

@Serializable
data class EntityHold(@Serializable(with = UUIDSerializer::class) val id: UUID) {
	val get: Entity get() = orNull!!
	val orNull: Entity? get() = Bukkit.getEntity(id)
}

val Entity.hold get() = EntityHold(uniqueId)
