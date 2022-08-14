package me.fanhua.piggies

import me.fanhua.piggies.tools.data.asJson
import me.fanhua.piggies.tools.data.asString
import me.fanhua.piggies.tools.data.get
import me.fanhua.piggies.Piggies as PiggiesMain

object PiggiesVersions {

	val Piggies: String = PiggiesMain.plugin.description.version

	val Kotlin: String
	val Coroutines: String
	val Serialization: String
	val AtomicFU: String
	val Datetime: String

	init {
		val versions = PiggiesMain.getResource("versions.json").asJson()
		Kotlin = versions["kotlin"].asString!!
		Coroutines = versions["coroutines"].asString!!
		Serialization = versions["serialization"].asString!!
		@Suppress("SpellCheckingInspection")
		AtomicFU = versions["atomicfu"].asString!!
		Datetime = versions["datetime"].asString!!
	}

}
