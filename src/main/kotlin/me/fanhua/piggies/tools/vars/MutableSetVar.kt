package me.fanhua.piggies.tools.vars

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutableSetVar<T> protected constructor(target: MutableSet<T>, iv: IntVersion)
	: ComplexVar<MutableSet<T>>(target, iv), SetVar<T> by ChangedSet(target, iv) {

	constructor(target: MutableSet<T>): this(target, IntVersion())

	override fun toString(): String = "MutableSetVar(set=$target, version=$version)"

	open class ChangedSet<T>(target: MutableSet<T>, iv: IntVersion)
		: ComplexVar<MutableSet<T>>(target, iv), SetVar<T>, MutableSet<T> by target {

		override val version: Int get() = iv.version

		override fun clear() {
			if (target.isEmpty()) return
			target.clear()
			iv.version++
		}

		override fun add(element: T): Boolean
			= target.add(element).apply { if (this) iv.version++ }

		override fun addAll(elements: Collection<T>): Boolean
			= target.addAll(elements).apply { if (this) iv.version++ }

		override fun remove(element: T): Boolean
			= target.remove(element).apply { if (this) iv.version++ }

		override fun removeAll(elements: Collection<T>): Boolean
			= target.removeAll(elements.toSet()).apply { if (this) iv.version++ }

		override fun retainAll(elements: Collection<T>): Boolean
			= target.retainAll(elements.toSet()).apply { if (this) iv.version++ }

		override fun iterator(): MutableIterator<T>
			= MutableCollectionVar.ChangedIterator(target.iterator(), iv)

		override fun toString(): String = "MutableSetVar.Set(set=$target, version=$version)"

	}

}

inline val <T> MutableSet<T>.varOf: SetVar<T> get() = MutableSetVar(this)
@Suppress("NOTHING_TO_INLINE")
inline fun <T> varSetOf(): SetVar<T> = mutableSetOf<T>().varOf
@Suppress("NOTHING_TO_INLINE")
inline fun <T> varSetOf(vararg elements: T): SetVar<T> = mutableSetOf(*elements).varOf
inline val <T> Val<MutableSet<T>>.readonly: Val<Set<T>> get() = this
