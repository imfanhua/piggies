package me.fanhua.piggies.tools.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack

object ItemStackSerializer: KSerializer<ItemStack> {

	private val serializer = ByteArraySerializer()
	@Suppress("OPT_IN_USAGE")
	override val descriptor = SerialDescriptor("ItemStack", serializer.descriptor)
	override fun serialize(encoder: Encoder, value: ItemStack)
		= encoder.encodeSerializableValue(serializer, value.serializeAsBytes())
	override fun deserialize(decoder: Decoder): ItemStack
		= ItemStack.deserializeBytes(decoder.decodeSerializableValue(serializer))

}
