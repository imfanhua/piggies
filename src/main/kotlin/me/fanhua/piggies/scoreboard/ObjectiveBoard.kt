package me.fanhua.piggies.scoreboard

import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard
import kotlin.properties.Delegates

abstract class ObjectiveBoard(val scoreboard: Scoreboard, val id: String, title: String, slot: DisplaySlot? = null, render: RenderType = RenderType.INTEGER) {

	private var lastObjective: Objective? = scoreboard.getObjective(id)

	var title by Delegates.observable(title) { _, _, value -> lastObjective?.displayName = value }
	var slot by Delegates.observable(slot) { _, _, value -> lastObjective?.displaySlot = value }
	var render by Delegates.observable(render) { _, _, value -> lastObjective?.renderType = value }

	val objective: Objective
		get() = lastObjective ?: recreate()

	init {
		lastObjective?.let {
			it.displayName = title
			it.displaySlot = slot
			it.renderType = render
		}
	}

	fun recreate(): Objective {
		remove()
		return scoreboard.registerNewObjective(id, "dummy", title).apply {
			render(this)
			displaySlot = slot
			renderType = render

			lastObjective = this
		}
	}

	protected open fun render(objective: Objective) {}

	fun remove() = apply {
		lastObjective?.unregister()
		lastObjective = null
	}

}
