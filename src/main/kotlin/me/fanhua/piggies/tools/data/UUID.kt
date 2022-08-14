package me.fanhua.piggies.tools.data

import java.math.BigInteger
import java.util.*

val String.isUUID: Boolean get() = (length == 36 && get(8) == '-') || length == 32

val String.asUUIDOrNull: UUID? get() = if (isUUID) asUUID else null

val String.asUUID: UUID get()
	= if (get(8) == '-') UUID.fromString(this)
	else UUID(BigInteger(substring(0, 16), 16).toLong(), BigInteger(substring(16, 32), 16).toLong())

val UUID.asPureString get() = toString().filterNot { it == '-' }
