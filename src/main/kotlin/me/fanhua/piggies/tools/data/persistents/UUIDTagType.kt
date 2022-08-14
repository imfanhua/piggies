package me.fanhua.piggies.tools.data.persistents

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import java.nio.ByteBuffer
import java.util.*

object UUIDTagType : PersistentDataType<ByteArray, UUID> {

	override fun getPrimitiveType(): Class<ByteArray> = ByteArray::class.java
	override fun getComplexType(): Class<UUID> = UUID::class.java

	override fun toPrimitive(complex: UUID, context: PersistentDataAdapterContext): ByteArray
		= ByteBuffer.wrap(byteArrayOf(16))
			.putLong(complex.mostSignificantBits)
			.putLong(complex.leastSignificantBits)
			.array()

	override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): UUID
		= ByteBuffer.wrap(primitive).let {
			val mostSigBits = it.long
			val leastSigBits = it.long
			UUID(mostSigBits, leastSigBits)
		}

}
