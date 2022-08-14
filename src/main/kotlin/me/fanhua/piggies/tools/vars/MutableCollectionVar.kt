package me.fanhua.piggies.tools.vars

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutableCollectionVar<T> protected constructor(target: MutableCollection<T>, iv: IntVersion)
	: ComplexVar<MutableCollection<T>>(target, iv), CollectionVar<T> by ChangedCollection(target, iv) {

	constructor(target: MutableCollection<T>): this(target, IntVersion())

	override fun toString(): String = "MutableCollectionVar(collection=$target, version=$version)"

	open class ChangedCollection<T>(target: MutableCollection<T>, iv: IntVersion)
		: ComplexVar<MutableCollection<T>>(target, iv), CollectionVar<T>, MutableCollection<T> by target {

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
			= ChangedIterator(target.iterator(), iv)

		override fun toString(): String = "MutableCollectionVar.Collection(collection=$target, version=$version)"

	}

	open class ChangedIterator<T>(target: MutableIterator<T>, iv: IntVersion)
		: ComplexWithVersion<MutableIterator<T>>(target, iv), MutableIterator<T> by target {

		override fun remove()
			= target.remove().apply { iv.version++ }

		override fun toString(): String = "MutableCollectionVar.Iterator(iterator=$target, version=${iv.version})"

	}

}

inline val <T> MutableCollection<T>.varOf: CollectionVar<T> get() = MutableCollectionVar(this)
inline val <T> Val<MutableCollection<T>>.readonly: Val<Collection<T>> get() = this
