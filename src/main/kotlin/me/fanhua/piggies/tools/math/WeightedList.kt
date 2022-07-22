package me.fanhua.piggies.tools.math

import java.util.*
import kotlin.random.Random

class WeightedList<T>() {

	private val map: NavigableMap<Double, T> = TreeMap()
	private var total = 0.0

	constructor(vararg list: Pair<Double, T>) : this() {
		for ((weight, item) in list) {
			total += weight
			map[total] = item
		}
	}

	fun add(weight: Double, item: T): WeightedList<T> {
		if (weight <= 0) return this
		total += weight
		map[total] = item
		return this
	}

	operator fun get(value: Double): T = map.higherEntry(value * total).value

	fun next(random: Random) = get(random.nextDouble())
	val next get() = get(Random.nextDouble())

}