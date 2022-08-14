package me.fanhua.piggies

import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.title

object Piggies : PiggyPlugin() {

	internal class IPlugin : Plugin(Piggies)

	override fun load() {
		logger.apply {
			title("Versions")
			info("+ Piggies: Ver.${PiggiesVersions.Piggies}")
			info("+ Kotlin: Ver.${PiggiesVersions.Kotlin}")
			info("+ Coroutines: Ver.${PiggiesVersions.Coroutines}")
			info("+ Serialization: Ver.${PiggiesVersions.Serialization}")
			info("+ AtomicFU: Ver.${PiggiesVersions.AtomicFU}")
			info("+ Datetime: Ver.${PiggiesVersions.Datetime}")
		}
	}

}
