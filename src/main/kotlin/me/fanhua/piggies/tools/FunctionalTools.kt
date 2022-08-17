package me.fanhua.piggies.tools

@Suppress("NOTHING_TO_INLINE")
inline fun Any?.void() {}

data class OK<T>(val value: T)

inline fun ok(run: () -> Unit) =
	try {
		run()
		true
	} catch (_: Throwable) {
		false
	}

inline fun <T> must(run: () -> T): T? =
	try {
		run()
	} catch (_: Throwable) {
		null
	}

inline fun <T, B> Array<T>.streamOrNull(starter: (T) -> B, transform: B.(T) -> B): B? {
	var last: B? = null
	forEach { last = last.let { last -> if (last == null) starter(it) else transform(last, it) } }
	return last
}

inline fun <T, B> Collection<T>.streamOrNull(starter: (T) -> B, transform: B.(T) -> B): B? {
	var last: B? = null
	forEach { last = last.let { last -> if (last == null) starter(it) else transform(last, it) } }
	return last
}

inline fun <T, B> Array<T>.streamTo(starter: (T) -> B, transform: B.(T) -> B): B
	= streamOrNull(starter, transform)!!

inline fun <T, B> Collection<T>.streamTo(starter: (T) -> B, transform: B.(T) -> B): B
	= streamOrNull(starter, transform)!!

inline fun <T, B> Array<T>.streamFrom(value: B, transform: B.(T) -> B): B {
	var last: B = value
	forEach { last = transform(last, it) }
	return last
}

inline fun <T, B> Collection<T>.streamFrom(value: B, transform: B.(T) -> B): B {
	var last: B = value
	forEach { last = transform(last, it) }
	return last
}

inline fun <T: Map<*, *>> T.ifNotEmpty(transform: (T) -> T): T =
	if (isNotEmpty()) transform(this) else this

inline fun <T> Array<T>.ifNotEmpty(transform: (Array<T>) -> Array<T>): Array<T> =
	if (isNotEmpty()) transform(this) else this

inline fun <T: Collection<*>> T.ifNotEmpty(transform: (T) -> T): T =
	if (isNotEmpty()) transform(this) else this
