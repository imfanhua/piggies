package me.fanhua.piggies.tools.math

import kotlin.random.Random

fun <T> Random.nextIndex(array: Array<T>)
	= nextInt(array.size)
fun <T> Random.nextIndexOrNull(array: Array<T>)
	= if (array.isEmpty()) -1 else nextInt(array.size)

fun <T> Random.nextIndex(list: Collection<T>)
	=  nextInt(list.size)
fun <T> Random.nextIndexOrNull(list: Collection<T>)
	= if (list.isEmpty()) -1 else nextInt(list.size)

fun <T> Random.next(list: WeightedList<T>) = list.next(this)

fun Random.rate(rate: Double) = nextDouble() < rate
