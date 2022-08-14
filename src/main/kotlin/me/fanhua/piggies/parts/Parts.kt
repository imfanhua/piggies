package me.fanhua.piggies.parts

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import me.fanhua.piggies.Piggies
import me.fanhua.piggies.parts.impl.SerializerPartFactory
import me.fanhua.piggies.parts.impl.TempPartFactory
import me.fanhua.piggies.players.events.PlayerLeaveEvent
import me.fanhua.piggies.plugins.PluginKey
import me.fanhua.piggies.plugins.last
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
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
			PARTS.remove(this)
		}

	}

	private val PARTS: MutableList<Part<*>> = arrayListOf()

	init {
		Piggies.on(object: Listener {

			@EventHandler(priority = EventPriority.MONITOR)
			fun onServerTickEvent(event: PlayerLeaveEvent.Last) {
				val player = event.player
				player.uniqueId.let { id -> PARTS.forEach { part -> part.remove(id, player) } }
			}

		})

		Piggies.logger.info("+ #[Parts]")
	}

	@Suppress("OPT_IN_USAGE")
	inline fun <reified P> persistent(key: PluginKey, noinline factory: () -> P): IPart<P> = persistent(key, Cbor, factory)
	inline fun <reified P> persistent(key: PluginKey, format: BinaryFormat, noinline factory: () -> P): IPart<P> = persistent(key, format, format.serializersModule.serializer(), factory)
	fun <P> persistent(key: PluginKey, format: BinaryFormat, serializer: KSerializer<P>, factory: () -> P): IPart<P>
		= part(SerializerPartFactory(key.key, format, serializer, factory)).apply { key.plugin.last(::unloadAll) }

	fun <P> temp(factory: () -> P): IPart<P> = part(TempPartFactory(factory))
	fun <P> new(factory: IPartFactory<P>): IPart<P> = part(factory)

	private fun <P> part(factory: IPartFactory<P>): Part<P> = Part(factory).apply(PARTS::add)

}
