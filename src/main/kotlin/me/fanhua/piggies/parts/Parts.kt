package me.fanhua.piggies.parts

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import me.fanhua.piggies.Piggies
import me.fanhua.piggies.players.events.PlayerLeaveEvent
import me.fanhua.piggies.parts.impl.SerializerPartFactory
import me.fanhua.piggies.parts.impl.TempPartFactory
import me.fanhua.piggies.tools.plugins.logger
import org.bukkit.Bukkit
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

		fun remove(id: UUID, player: Player) = parts.remove(id)?.let { unload(player, it) }

		fun unload(player: Player, part: P) {
			(part as? IPlayerPart)?.unload(player)
			factory.save(player, part)
		}

		fun unloadAll() {
			parts.forEach { (id, data) -> Bukkit.getPlayer(id)?.let { unload(it, data) } }
			parts.clear()
		}

	}

	private val PARTS: MutableList<Part<*>> = arrayListOf()

	init {
		PlayerLeaveEvent.lasts.add {
			val player = it.player
			player.uniqueId.let { id -> PARTS.forEach { part -> part.remove(id, player) } }
		}

		//FIXME:
		// Piggies.lasts.add(::unloadAll)

		Piggies.logger.info("+ #[Parts]")
	}

	@Suppress("OPT_IN_USAGE")
	inline fun <reified P> persistent(key: NamespacedKey, noinline factory: () -> P): IPart<P> = persistent(key, Cbor, factory)
	inline fun <reified P> persistent(key: NamespacedKey, format: BinaryFormat, noinline factory: () -> P): IPart<P> = persistent(key, format, format.serializersModule.serializer(), factory)
	fun <P> persistent(key: NamespacedKey, format: BinaryFormat, serializer: KSerializer<P>, factory: () -> P): IPart<P>
		= new(SerializerPartFactory(key, format, serializer, factory))
	fun <P> temp(factory: () -> P): IPart<P> = new(TempPartFactory(factory))
	fun <P> new(factory: IPartFactory<P>): IPart<P> = Part(factory).apply(PARTS::add)

	private fun unloadAll() =
		PARTS.forEach(Part<*>::unloadAll)

}