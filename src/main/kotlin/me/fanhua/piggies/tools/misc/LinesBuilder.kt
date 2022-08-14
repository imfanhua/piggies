package me.fanhua.piggies.tools.misc

class LinesBuilder(val lines: MutableList<String>) {

	var first = true

	fun split(line: String) = apply {
		if (!first) lines.add(line)
		else first = false
	}

	fun line(line: String) = apply { lines.add(line) }

}
