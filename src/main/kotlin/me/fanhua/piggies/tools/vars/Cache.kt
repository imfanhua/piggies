package me.fanhua.piggies.tools.vars

open class Cache<out T>(val target: Val<T>) : Val<T> by target {

	var current: Int = target.version
		private set

	val isChanged get() = current != target.version

	fun use() = target.version.let {
		if (current == it) false else {
			current = it
			true
		}
	}

	fun used() = apply { current = target.version }

	override fun equals(other: Any?): Boolean
		= this === other || javaClass == other?.javaClass && (other as Cache<*>).let {
			target == it.target && current == it.current
		}

	override fun hashCode(): Int = 31 * target.hashCode() + version

	override fun isValueEquals(other: Any?): Boolean = target == other

	override val valueHashCode: Int get() = target.hashCode()
	override val valueToString: String get() = target.toString()

	override fun toString(): String {
		return "Cache(target=$target, current=$current)"
	}

}

val <T> Val<T>.newCache get() = Cache(this)
