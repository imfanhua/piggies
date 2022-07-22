package me.fanhua.piggies.parts.impl

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import me.fanhua.piggies.parts.IPartFactory
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

class SerializerPartFactory<P>(val key: NamespacedKey, val format: BinaryFormat, val serializer: KSerializer<P>, val factory: () -> P) : IPartFactory<P> {

	override fun load(player: Player): P
		= player.persistentDataContainer.get(key, PersistentDataType.BYTE_ARRAY)?.let {
			format.decodeFromByteArray(serializer, it)
		} ?: factory()

	override fun save(player: Player, part: P) {
		player.persistentDataContainer.set(key, PersistentDataType.BYTE_ARRAY, format.encodeToByteArray(serializer, part))
	}

}