package me.fanhua.piggies.tools.vars

abstract class ComplexVar<T>(target: T, iv: IntVersion): ComplexWithVersion<T>(target, iv), Val<T> {

	override fun isValueEquals(other: Any?): Boolean = target == other

	override val valueHashCode: Int get() = target.hashCode()
	override val valueToString: String get() = target.toString()

}
