package me.fanhua.piggies.tools.vars

class MutableVar<T>(initialValue: T) : Var<T> {

	override var value: T = initialValue
		set(value) {
			if (field == value) return
			field = value
			version++
		}

	override var version: Int = 0
		private set

	override fun equals(other: Any?): Boolean
		= this === other || javaClass == other?.javaClass && (other as MutableVar<*>).let {
			value == it.value && version == it.version
		}
	override fun hashCode(): Int = 31 * (value?.hashCode() ?: 0) + version
	override fun toString(): String = "MutableVar(value=$value, version=$version)"

}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> varOf(value: T): Var<T> = MutableVar(value)
inline val <T> Var<T>.readonly get(): Val<T> = this
