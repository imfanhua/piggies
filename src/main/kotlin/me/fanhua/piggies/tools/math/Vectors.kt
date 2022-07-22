package me.fanhua.piggies.tools.math

import org.bukkit.Location

fun Location.forward(distance: Float) = add(direction.multiply(distance))
fun Location.forward(face: VectorFace, distance: Float) = forward(face, 90.0f, distance)
fun Location.forward(face: VectorFace, amount: Float, distance: Float)
	= add(Direction(this).forward(face, amount, distance))

fun Direction.forward(face: VectorFace, distance: Float) = forward(face, 90.0f, distance)
fun Direction.forward(face: VectorFace, amount: Float, distance: Float) = face.rotate(this, amount).forward(distance)
fun Direction.forward(distance: Float) = vector.multiply(distance)