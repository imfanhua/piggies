package me.fanhua.piggies.tools.misc

val NONE_TIME_PREFIX = emptyList<Pair<Long, String>>()

val DEFAULT_TIME_PREFIX = listOf(
	5L to "§4",
	10L to "§c",
	20L to "§6",
	30L to "§e",
)

fun Long.time(prefix: Collection<Pair<Long, String>> = DEFAULT_TIME_PREFIX): String {
	val min = this / 60
	val sec = this - min * 60
	val color = prefix.firstOrNull { this <= it.first } ?: "§f"
	return "$color${"$min".padStart(2, '0')}§7:$color${"$sec".padStart(2, '0')}"
}
