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
