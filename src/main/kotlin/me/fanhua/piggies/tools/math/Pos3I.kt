package me.fanhua.piggies.tools.math

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.NumberConversions.square
import org.bukkit.util.Vector
import kotlin.math.sqrt

@Serializable
data class Pos3I(var x: Int, var y: Int, var z: Int) {

	constructor(pos: Vector) : this(pos.blockX, pos.blockY, pos.blockZ)
	constructor(pos: Location) : this(pos.blockX, pos.blockY, pos.blockZ)

	fun x(x: Int) = Pos3I(x, y, z)
	fun y(y: Int) = Pos3I(x, y, z)
	fun z(z: Int) = Pos3I(x, y, z)
	fun xy(x: Int, y: Int) = Pos3I(x, y, z)
	fun xz(x: Int, z: Int) = Pos3I(x, y, z)
	fun yz(y: Int, z: Int) = Pos3I(x, y, z)

	infix fun lengthBy(pos: Pos3I) = sqrt(square((x - pos.x).toDouble()) + square((y - pos.y).toDouble()) + square((z - pos.z).toDouble()))

	operator fun plus(b: Pos3I) = Pos3I(x + b.x, y + b.y, z + b.z)
	operator fun minus(b: Pos3I) = Pos3I(x - b.x, y - b.y, z - b.z)
	operator fun times(b: Pos3I) = Pos3I(x * b.x, y * b.y, z * b.z)
	operator fun div(b: Pos3I) = Pos3I(x / b.x, y / b.y, z / b.z)
	operator fun rem(b: Pos3I) = Pos3I(x % b.x, y % b.y, z % b.z)

	operator fun plusAssign(b: Pos3I) {
		x += b.x
		y += b.y
		z += b.z
	}

	operator fun minusAssign(b: Pos3I) {
		x -= b.x
		y -= b.y
		z -= b.z
	}

	operator fun timesAssign(b: Pos3I) {
		x *= b.x
		y *= b.y
		z *= b.z
	}

	operator fun divAssign(b: Pos3I) {
		x /= b.x
		y /= b.y
		z /= b.z
	}

	operator fun remAssign(b: Pos3I) {
		x %= b.x
		y %= b.y
		z %= b.z
	}

	val vector get() = Vector(x.toDouble(), y.toDouble(), z.toDouble())
	fun loc(world: World) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

	operator fun get(world: World) = world.getBlockAt(x, y, z)

}
