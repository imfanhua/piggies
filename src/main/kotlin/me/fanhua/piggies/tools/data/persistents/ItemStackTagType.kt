package me.fanhua.piggies.tools.data.persistents

import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

object ItemStackTagType : PersistentDataType<ByteArray, ItemStack> {

	override fun getPrimitiveType(): Class<ByteArray> = ByteArray::class.java
	override fun getComplexType(): Class<ItemStack> = ItemStack::class.java

	override fun toPrimitive(complex: ItemStack, context: PersistentDataAdapterContext): ByteArray
		= complex.serializeAsBytes()

	override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): ItemStack
		= ItemStack.deserializeBytes(primitive)

}
