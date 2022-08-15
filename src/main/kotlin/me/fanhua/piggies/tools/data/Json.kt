package me.fanhua.piggies.tools.data

import kotlinx.serialization.json.*
import java.io.InputStream

@Suppress("OPT_IN_USAGE")
fun InputStream?.asJson(): JsonElement? = this?.let(Json::decodeFromStream)

val JsonElement?.asObject: JsonObject? get() = this as? JsonObject
val JsonElement?.asArray: JsonArray? get() = this as? JsonArray
operator fun JsonElement?.get(key: String): JsonElement? = (this as? JsonObject)?.let { it[key] }
operator fun JsonElement?.get(key: Int): JsonElement? = (this as? JsonArray)?.let { it[key] }

val JsonElement?.asString: String? get() = (this as? JsonPrimitive)?.content
val JsonElement?.asInt: Int? get() = (this as? JsonPrimitive)?.content?.toIntOrNull()
val JsonElement?.asLong: Long? get() = (this as? JsonPrimitive)?.content?.toLongOrNull()
val JsonElement?.asDouble: Double? get() = (this as? JsonPrimitive)?.content?.toDoubleOrNull()
val JsonElement?.asFloat: Float? get() = (this as? JsonPrimitive)?.content?.toFloatOrNull()
val JsonElement?.asBoolean: Boolean? get() = (this as? JsonPrimitive)?.content?.toBooleanStrictOrNull()
