package me.fanhua.piggies.tools.math

import org.bukkit.Location
import org.bukkit.util.Vector

enum class VectorFace {

	UP {
		override fun rotatePitch(pitch: Float, amount: Float) = pitch - amount
		override fun rotateYaw(yaw: Float, amount: Float) = 0.0F
	},
	DOWN {
		override fun rotatePitch(pitch: Float, amount: Float) = pitch + amount
		override fun rotateYaw(yaw: Float, amount: Float) = 0.0F
	},
	LEFT {
		override fun rotatePitch(pitch: Float, amount: Float) = 0.0F
		override fun rotateYaw(yaw: Float, amount: Float) = yaw - amount
	},
	RIGHT {
		override fun rotatePitch(pitch: Float, amount: Float) = 0.0F
		override fun rotateYaw(yaw: Float, amount: Float) = yaw + amount
	},
	;

	fun rotate(location: Location, amount: Float)
		= location.apply {
			pitch = rotatePitch(location.pitch, amount)
			yaw = rotateYaw(location.yaw, amount)
		}
	fun rotate(direction: Vector, amount: Float)
		= rotate(Direction(direction), amount).vector
	fun rotate(direction: Direction, amount: Float)
		= Direction(rotatePitch(direction.pitch, amount), rotateYaw(direction.yaw, amount))

	protected abstract fun rotateYaw(yaw: Float, amount: Float): Float
	protected abstract fun rotatePitch(pitch: Float, amount: Float): Float

}