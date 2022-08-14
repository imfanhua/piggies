package me.fanhua.piggies.tools.worlds

import me.fanhua.piggies.tools.math.Pos3I
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.math.ceil

fun Pos3I.area(distance: Float): Stream<Pos3I> {
	val width = ceil(distance.toDouble()).toInt()
	val width2 = width * 2
	val depth2 = width2 * width2
	val pointX = x - width
	val pointY = y - width
	val pointZ = z - width
	return IntStream.range(0, width2 * depth2)
		.mapToObj {
			val z = it / depth2
			val part = it % depth2
			val y = part / width2
			Pos3I(pointX + part % width2, pointY + y, pointZ + z)
		}.filter { it lengthBy this <= distance }
}
