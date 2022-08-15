package me.fanhua.piggies.lang

import org.bukkit.Keyed
import org.bukkit.NamespacedKey

class LanguageSet<T: Keyed>(private val map: Map<String, String>) : Map<String, String> by map {

	operator fun get(key: NamespacedKey) = get(key.key)
	fun containsKey(key: NamespacedKey) = containsKey(key.key)

	operator fun get(key: T) = get(key.key)
	fun containsKey(key: T) = containsKey(key.key)

}
