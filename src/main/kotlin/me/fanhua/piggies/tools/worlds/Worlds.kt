package me.fanhua.piggies.tools.worlds

import me.fanhua.piggies.tools.copyDirectoryTo
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import java.io.File
import java.io.IOException

object Worlds {

	data class Info(val creator: WorldCreator, val exists: Boolean) {
		constructor(creator: WorldCreator): this(creator, exists(creator))
	}

	private val PATH = File("plugins", "templates")
	const val VOID =
		"{\"structures\": {\"structures\": {}}, \"layers\": [{\"block\": \"air\", \"height\": 1}], \"biome\":\"the_void\"}"

	init {
		PATH.mkdirs()
	}

	fun exists(creator: WorldCreator) = File(creator.name()).exists()

	fun newVoidCreator(name: String): WorldCreator
		= WorldCreator(name)
			.type(WorldType.FLAT)
			.generatorSettings(VOID)
			.generateStructures(false)

	fun newVoid(name: String): Info = Info(newVoidCreator(name))

	fun newVoidFrom(name: String, template: String): Info {
		val file = File(name)
		val exists = file.exists()
		if (!exists) {
			if (!File(PATH, template).copyDirectoryTo(file))
				throw IOException("Template \"$name\" not found!")
		}
		return Info(newVoidCreator(name), exists)
	}

}
