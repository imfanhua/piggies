package me.fanhua.piggies.tools.vars

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutableListVar<T> protected constructor(target: MutableList<T>, iv: IntVersion)
	: ComplexVar<MutableList<T>>(target, iv), ListVar<T> by ChangedList(target, iv) {

	constructor(target: MutableList<T>): this(target, IntVersion())

	override fun toString(): String = "MutableListVar(list=$target, version=$version)"

	open class ChangedList<T>(target: MutableList<T>, iv: IntVersion)
		: ComplexVar<MutableList<T>>(target, iv), ListVar<T>, MutableList<T> by target {

		override val version: Int get() = iv.version

		override fun clear() {
			if (target.isEmpty()) return
			target.clear()
			iv.version++
		}

		override fun add(index: Int, element: T)
			= target.add(index, element).apply { iv.version++ }

		override fun add(element: T): Boolean
			= target.add(element).apply { if (this) iv.version++ }

		override fun addAll(elements: Collection<T>): Boolean
			= target.addAll(elements).apply { if (this) iv.version++ }

		override fun addAll(index: Int, elements: Collection<T>): Boolean
			= target.addAll(index, elements).apply { if (this) iv.version++ }

		override fun set(index: Int, element: T): T
			= target.set(index, element).apply { if (this != element) iv.version++ }

		override fun removeAt(index: Int): T
			= target.removeAt(index).apply { iv.version++ }

		override fun remove(element: T): Boolean
			= target.remove(element).apply { if (this) iv.version++ }

		override fun removeAll(elements: Collection<T>): Boolean
			= target.removeAll(elements).apply { if (this) iv.version++ }

		override fun retainAll(elements: Collection<T>): Boolean
			= target.retainAll(elements).apply { if (this) iv.version++ }

		override fun subList(fromIndex: Int, toIndex: Int): MutableList<T>
			= ChangedList(target.subList(fromIndex, toIndex), iv)

		override fun listIterator(): MutableListIterator<T>
			= ChangedListIterator(target.listIterator(), iv)

		override fun listIterator(index: Int): MutableListIterator<T>
			= ChangedListIterator(target.listIterator(index), iv)

		override fun toString(): String = "MutableListVar.List(list=$target, version=$version)"

	}

	open class ChangedListIterator<T>(target: MutableListIterator<T>, iv: IntVersion)
		: ComplexWithVersion<MutableListIterator<T>>(target, iv), MutableListIterator<T> by target {

		override fun add(element: T)
			= target.add(element).apply { iv.version++ }

		override fun remove()
			= target.remove().apply { iv.version++ }

		override fun set(element: T)
			= target.set(element).apply { iv.version++ }

		override fun toString(): String = "MutableListVar.ListIterator(list=$target, version=$version)"

	}

}

inline val <T> MutableList<T>.varOf: ListVar<T> get() = MutableListVar(this)
@Suppress("NOTHING_TO_INLINE")
inline fun <T> varListOf(): ListVar<T> = mutableListOf<T>().varOf
@Suppress("NOTHING_TO_INLINE")
inline fun <T> varListOf(vararg elements: T): ListVar<T> = mutableListOf(*elements).varOf
inline val <T> Val<MutableList<T>>.readonly: Val<List<T>> get() = this
