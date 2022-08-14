package me.fanhua.piggies.tools.vars

open class ListCache<T>(target: ListVar<T>): Cache<MutableList<T>>(target), MutableList<T> by target
open class SetCache<T>(target: SetVar<T>): Cache<MutableSet<T>>(target), MutableSet<T> by target
open class CollectionCache<T>(target: CollectionVar<T>): Cache<MutableCollection<T>>(target), MutableCollection<T> by target
open class MapCache<K, V>(target: MapVar<K, V>): Cache<MutableMap<K, V>>(target), MutableMap<K, V> by target

val <T> ListVar<T>.newCache get() = ListCache(this)
val <T> SetVar<T>.newCache get() = SetCache(this)
val <T> CollectionVar<T>.newCache get() = CollectionCache(this)
val <K, V> MapVar<K, V>.newCache get() = MapCache(this)
