package me.fanhua.piggies.tools.vars

abstract class ComplexWithVersion<T>(protected val target: T, protected val iv: IntVersion): WithVersion {

	override val version: Int get() = iv.version

	override fun equals(other: Any?): Boolean
		= this === other || javaClass == other?.javaClass && (other as ComplexWithVersion<*>).let {
			target == it.target && version == it.version
		}
	override fun hashCode(): Int = 31 * target.hashCode() + version

}
