package me.fanhua.piggies.scoreboard

import me.fanhua.piggies.tools.misc.LinesBuilder
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard

class BoardDisplay(scoreboard: Scoreboard, id: String, title: String, slot: DisplaySlot? = DisplaySlot.SIDEBAR, render: RenderType = RenderType.INTEGER): ObjectiveBoard(scoreboard, id, title, slot, render) {

	private val last: MutableList<String> = arrayListOf()

	val strings: List<String> get() = last

	fun display(vararg strings: String) = apply {
		last.clear()
		last.addAll(strings)
	}

	val builder get() = last.let {
		it.clear()
		LinesBuilder(it)
	}

	inline fun display(builder: LinesBuilder.() -> Unit) = apply {
		this.builder.apply(builder)
	}

	override fun render(objective: Objective) {
		var i = last.size
		for (line in last)
			objective.getScore(line).score = --i
	}

}