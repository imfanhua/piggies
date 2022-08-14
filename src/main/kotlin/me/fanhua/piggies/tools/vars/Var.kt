package me.fanhua.piggies.tools.vars

interface Val<out T>: WithVersion {
	val value: T
	fun isValueEquals(other: Any?): Boolean = value == other
	val valueHashCode: Int get() = value.hashCode()
	val valueToString: String get() = value.toString()
}

interface Var<T> : Val<T> {
	override var value: T
}

interface ListVar<T>: Val<MutableList<T>>, MutableList<T> {
	override val value: MutableList<T> get() = this
}

interface SetVar<T>: Val<MutableSet<T>>, MutableSet<T> {
	override val value: MutableSet<T> get() = this
}

interface CollectionVar<T>: Val<MutableCollection<T>>, MutableCollection<T> {
	override val value: MutableCollection<T> get() = this
}

interface MapVar<K, V>: Val<MutableMap<K, V>>, MutableMap<K, V> {
	override val value: MutableMap<K, V> get() = this
}
