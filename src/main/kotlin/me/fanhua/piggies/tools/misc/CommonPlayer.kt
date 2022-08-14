package me.fanhua.piggies.tools.misc

import me.fanhua.piggies.tools.data.asUUID
import org.bukkit.World
import java.util.*

enum class CommonPlayer(id: String, val environment: World.Environment) {

	@Suppress("SpellCheckingInspection")
	FANHUA("27e30fee5cb846999bd0128bd19309a3", World.Environment.NORMAL),
	@Suppress("SpellCheckingInspection")
	KAMUSAMA("055ae9065e48407784706254c15077b7", World.Environment.NETHER),
	UNCLE_RED("30ecf6ccf46f405584c4c8b980c0d6d7", World.Environment.NORMAL),
	@Suppress("SpellCheckingInspection")
	MELOR_("442ecf549cac4010b3fb70dc57819ee8", World.Environment.NORMAL),
	HEI_MAO("87cc1b9592fa448ca4fc5164b38fbc47", World.Environment.NORMAL),
	@Suppress("SpellCheckingInspection")
	BADCEN("a3c7d62a311c4a28b02c1ba2e27f9e67", World.Environment.THE_END),
	;

	val id = id.asUUID

	companion object {

		private val MAP = values().associateBy { it.id }.toMutableMap()

		fun fromId(id: UUID): CommonPlayer? = MAP[id]

		fun fromIdOrRandom(id: UUID)
			= MAP.computeIfAbsent(id) { values().random() }

	}

}
