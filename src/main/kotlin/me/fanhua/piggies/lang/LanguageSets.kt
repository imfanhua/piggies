package me.fanhua.piggies.lang

import me.fanhua.piggies.tools.void
import org.bukkit.Keyed

class LanguageSets {

	inner class SetRule<T: Keyed>(private val map: MutableMap<String, String>) {

		private var format: IRuleFormat = NoRuleFormat

		val no get() = apply { format = NoRuleFormat }
		val path get() = apply { format = PathRuleFormat }

		fun with(startsWith: String, endsWith: String) = apply {
			rules.add(LanguageSetRule(map, TrimRule(startsWith, endsWith), format))
		}

		fun with(startsWith: String) = apply {
			rules.add(LanguageSetRule(map, TrimStartRule(startsWith), format))
		}

	}

	private val rules = mutableListOf<LanguageSetRule>()

	fun <T: Keyed> set(builder: SetRule<T>.() -> Unit): LanguageSet<T> = mutableMapOf<String, String>().let {
		LanguageSet<T>(it).apply { SetRule<T>(it).apply(builder) }
	}

	fun addAll(map: Map<String, String>) = map.forEach(::add)

	fun add(key: String, value: String)
		= rules.any { it.use(key, value) }.void()

}

private class LanguageSetRule(private val map: MutableMap<String, String>, private val rule: IRule, private val format: IRuleFormat) {
	fun use(key: String, value: String): Boolean
		= rule.use(key)?.let(format::format)?.let { true.apply { map[it] = value } } ?: false
}

private interface IRule {
	fun use(key: String): String?
}

private class TrimRule(private val startsWith: String, private val endsWith: String): IRule {
	private val startsLength = startsWith.length
	private val endsLength = endsWith.length
	override fun use(key: String): String?
		= if (key.startsWith(startsWith) && key.endsWith(endsWith)) key.substring(startsLength, key.length - endsLength)
		else null
}

private class TrimStartRule(private val startsWith: String): IRule {
	private val startsLength = startsWith.length
	override fun use(key: String): String?
		= if (key.startsWith(startsWith) && key.endsWith(key)) key.substring(startsLength)
		else null
}

private interface IRuleFormat {
	fun format(key: String): String
}

object NoRuleFormat: IRuleFormat {
	override fun format(key: String) = key
}

object PathRuleFormat: IRuleFormat {
	override fun format(key: String) = key.replace('.', '/')
}
