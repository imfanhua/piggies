package me.fanhua.piggies.tools.vars

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutableMapVar<K, V> protected constructor(target: MutableMap<K, V>, iv: IntVersion)
	: ComplexVar<MutableMap<K, V>>(target, iv), MapVar<K, V> by ChangedMap(target, iv) {

	constructor(target: MutableMap<K, V>): this(target, IntVersion())

	override fun toString(): String = "MutableMapVar(map=$target, version=$version)"

	open class ChangedMap<K, V>(target: MutableMap<K, V>, iv: IntVersion)
		: ComplexVar<MutableMap<K, V>>(target, iv), MapVar<K, V>, MutableMap<K, V> by target {

		override val version: Int get() = iv.version

		override fun clear() {
			if (target.isEmpty()) return
			target.clear()
			iv.version++
		}

		override fun put(key: K, value: V): V?
			= target.put(key, value).apply { if (this != value) iv.version++ }

		override fun putAll(from: Map<out K, V>)
			= target.putAll(from).apply { iv.version++ }

		override fun remove(key: K): V?
			= if (containsKey(key)) target.remove(key).apply { iv.version++ }
			else null

		override fun remove(key: K, value: V): Boolean
			= target.remove(key, value).apply { if (this) iv.version++ }

		override val keys: MutableSet<K>
			get() = MutableSetVar.ChangedSet(target.keys, iv)

		override val values: MutableCollection<V>
			get() = MutableCollectionVar.ChangedCollection(target.values, iv)

		override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
			get() = ChangedEntrySet(target.entries, iv)

		override fun toString(): String = "MutableMapVar.Map(map=$target, version=$version)"

	}

	open class ChangedEntrySet<K, V>(target: MutableSet<MutableMap.MutableEntry<K, V>>, iv: IntVersion)
		: MutableSetVar.ChangedSet<MutableMap.MutableEntry<K, V>>(target, iv) {

		override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>>
			= ChangedEntryIterator(target.iterator(), iv)

		override fun toString(): String = "MutableMapVar.EntrySet(set=$target, version=$version)"

	}

	open class ChangedEntryIterator<K, V>(iterator: MutableIterator<MutableMap.MutableEntry<K, V>>, iv: IntVersion)
		: MutableCollectionVar.ChangedIterator<MutableMap.MutableEntry<K, V>>(iterator, iv) {

		override fun next(): MutableMap.MutableEntry<K, V>
			= ChangedEntry(target.next(), iv)

		override fun toString(): String = "MutableMapVar.EntryIterator(iterator=$target, version=${iv.version})"

	}

	open class ChangedEntry<K, V>(target: MutableMap.MutableEntry<K, V>, iv: IntVersion)
		: ComplexWithVersion<MutableMap.MutableEntry<K, V>>(target, iv), MutableMap.MutableEntry<K, V> by target {

		override fun setValue(newValue: V): V
			= target.setValue(newValue).apply { if (this != newValue) iv.version++ }

		override fun toString(): String = "MutableMapVar.Entry(entry=$target, version=${iv.version})"

	}

}

inline val <K, V> MutableMap<K, V>.varOf: MapVar<K, V> get() = MutableMapVar(this)
@Suppress("NOTHING_TO_INLINE")
inline fun <K, V> varMapOf(): MapVar<K, V> = mutableMapOf<K, V>().varOf
@Suppress("NOTHING_TO_INLINE")
inline fun <K, V> varMapOf(vararg pairs: Pair<K, V>): MapVar<K, V> = mutableMapOf(*pairs).varOf
inline val <K, V> Val<MutableMap<K, V>>.readonly: Val<Map<K, V>> get() = this
