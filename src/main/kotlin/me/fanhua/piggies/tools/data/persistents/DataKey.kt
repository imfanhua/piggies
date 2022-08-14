package me.fanhua.piggies.tools.data.persistents

import me.fanhua.piggies.tools.void
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

data class DataKey<T>(val key: NamespacedKey, val type: PersistentDataType<*, T>) {

	operator fun get(container: PersistentDataContainer?) = container?.get(key, type)
	operator fun set(container: PersistentDataContainer?, value: T & Any) = container?.set(key, type, value)
	fun remove(container: PersistentDataContainer?) = container?.remove(key).void()
	fun has(container: PersistentDataContainer?) = container?.has(key, type) == true
	fun any(container: PersistentDataContainer?) = container?.has(key) == true

}

operator fun <T> PersistentDataContainer?.get(key: DataKey<T>) = key[this]
operator fun <T> PersistentDataContainer?.set(key: DataKey<T>, value: T & Any) { key[this] = value }

fun <T> PersistentDataContainer?.remove(key: DataKey<T>) = key.remove(this)
fun <T> PersistentDataContainer?.has(key: DataKey<T>) = key.has(this)
fun <T> PersistentDataContainer?.any(key: DataKey<T>) = key.any(this)

fun PersistentDataContainer?.remove(key: NamespacedKey) = this?.remove(key).void()
fun PersistentDataContainer?.has(key: NamespacedKey, type: PersistentDataType<*, *>) = this?.has(key, type)
fun PersistentDataContainer?.any(key: NamespacedKey) = this?.has(key)

fun <T> NamespacedKey.with(type: PersistentDataType<*, T>) = DataKey(this, type)
