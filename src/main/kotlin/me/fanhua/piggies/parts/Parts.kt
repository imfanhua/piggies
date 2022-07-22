package me.fanhua.piggies.parts

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import me.fanhua.piggies.players.events.PlayerLeaveEvent
import me.fanhua.piggies.parts.impl.SerializerPartFactory
import me.fanhua.piggies.parts.impl.TempPartFactory
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import java.util.*

object Parts {

	private class Part<P>(val factory: IPartFactory<P>) : IPart<P> {

		private val parts: MutableMap<UUID, P> = hashMapOf()

		override fun get(player: Player): P
			= player.uniqueId.let {
				parts[it] ?: factory.load(player).apply {
					(this as? IPlayerPart)?.load(player)
					parts[it] = this
				}
			}

		fun remove(id: UUID, player: Player) = (parts.remove(id) as? IPlayerPart)?.unload(player)

	}

	private val PARTS: MutableList<Part<*>> = arrayListOf()

	init {
		PlayerLeaveEvent.lasts.add {
			val player = it.player
			player.uniqueId.let { id -> PARTS.forEach { part -> part.remove(id, player) } }
		}
	}

	@Suppress("OPT_IN_USAGE")
	inline fun <reified P> persistent(key: NamespacedKey, noinline factory: () -> P): IPart<P> = persistent(key, Cbor, factory)
	inline fun <reified P> persistent(key: NamespacedKey, format: BinaryFormat, noinline factory: () -> P): IPart<P> = persistent(key, format, format.serializersModule.serializer(), factory)
	fun <P> persistent(key: NamespacedKey, format: BinaryFormat, serializer: KSerializer<P>, factory: () -> P): IPart<P>
		= new(SerializerPartFactory(key, format, serializer, factory))
	fun <P> temp(factory: () -> P): IPart<P> = new(TempPartFactory(factory))
	fun <P> new(factory: IPartFactory<P>): IPart<P> = Part(factory).apply(PARTS::add)

}