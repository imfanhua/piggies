package me.fanhua.piggies

import me.fanhua.piggies.tools.data.asJson
import me.fanhua.piggies.tools.data.asString
import me.fanhua.piggies.tools.data.get
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.title
import kotlin.math.log

object Piggies : PiggyPlugin() {

	internal class IPlugin : Plugin(Piggies)

	override fun load() {
		logger.apply {
			val versions = getResource("versions.json").asJson()
			title("Versions")
			info("+ Piggies: Ver.${plugin.description.version}")
			info("+ Kotlin: Ver.${versions["kotlin"].asString}")
			info("+ Coroutines: Ver.${versions["coroutines"].asString}")
			info("+ Serialization: Ver.${versions["serialization"].asString}")
			info("+ AtomicFU: Ver.${versions["atomicfu"].asString}")
			info("+ Datetime: Ver.${versions["datetime"].asString}")
		}
	}

}
