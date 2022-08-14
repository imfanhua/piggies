package me.fanhua.piggies.tools.math

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.NumberConversions.square
import org.bukkit.util.Vector
import kotlin.math.*

@Serializable
data class Direction(var pitch: Float, var yaw: Float) {

	companion object {

		const val DOUBLE_PI = 2 * Math.PI

		fun getDirectionFromVector(x: Double, y: Double, z: Double): Pair<Float, Float>
			= if (x == 0.0 && z == 0.0)
				(if (y > 0) -90.0F else 90.toFloat()) to
				0.0F
			else
				Math.toDegrees(atan(-y / sqrt(square(x) + square(z)))).toFloat() to
				Math.toDegrees((atan2(-x, z) + DOUBLE_PI) % DOUBLE_PI).toFloat()

	}

	constructor(direction: Pair<Float, Float>) : this(direction.first, direction.second)
	constructor(x: Double, y: Double, z: Double) : this(getDirectionFromVector(x, y, z))
	constructor(direction: Vector) : this(getDirectionFromVector(direction.x, direction.y, direction.z))
	constructor(direction: Location) : this(direction.pitch, direction.yaw)

	fun pitch(pitch: Float) = Direction(pitch, yaw)
	fun yaw(yaw: Float) = Direction(pitch, yaw)

	operator fun plus(b: Direction) = Direction(pitch + b.pitch, yaw + b.yaw)
	operator fun minus(b: Direction) = Direction(pitch - b.pitch, yaw - b.yaw)
	operator fun times(b: Direction) = Direction(pitch * b.pitch, yaw * b.yaw)
	operator fun div(b: Direction) = Direction(pitch / b.pitch, yaw / b.yaw)
	operator fun rem(b: Direction) = Direction(pitch % b.pitch, yaw % b.yaw)

	operator fun plusAssign(b: Direction) {
		pitch += b.pitch
		yaw += b.yaw
	}

	operator fun minusAssign(b: Direction) {
		pitch -= b.pitch
		yaw -= b.yaw
	}

	operator fun timesAssign(b: Direction) {
		pitch *= b.pitch
		yaw *= b.yaw
	}

	operator fun divAssign(b: Direction) {
		pitch /= b.pitch
		yaw /= b.yaw
	}

	operator fun remAssign(b: Direction) {
		pitch %= b.pitch
		yaw %= b.yaw
	}

	val vector get() =
		cos(Math.toRadians(pitch.toDouble())).let { xz ->
			Vector(
				-xz * sin(Math.toRadians(yaw.toDouble())),
				-sin(Math.toRadians(pitch.toDouble())),
				xz * cos(Math.toRadians(yaw.toDouble()))
			)
		}

}
