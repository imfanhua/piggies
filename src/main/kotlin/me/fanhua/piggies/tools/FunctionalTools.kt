package me.fanhua.piggies.tools

@Suppress("NOTHING_TO_INLINE")
inline fun Any?.void() {}

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
