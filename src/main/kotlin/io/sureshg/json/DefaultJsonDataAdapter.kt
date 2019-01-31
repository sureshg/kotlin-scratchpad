package io.sureshg.json

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class DefaultJsonDataAdapter<T> private constructor(
    private val delegate: JsonAdapter<T>,
    private val defaultValue: T?
) : JsonAdapter<T>() {

    override fun fromJson(reader: JsonReader): T? = try {
        delegate.fromJsonValue(reader.readJsonValue())
    } catch (e: Exception) {
        defaultValue
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        delegate.toJson(writer, value)
    }

    companion object {
        fun <T : Any> newFactory(type: Class<T>, defaultValue: T?) = object : Factory {
            override fun create(
                requestedType: Type,
                annotations: Set<Annotation>,
                moshi: Moshi
            ): JsonAdapter<T>? {
                // val genericType = Types.newParameterizedType(List::class.java, type)
                if (type != requestedType) return null
                val nextAdapter = moshi.nextAdapter<T>(this, type, annotations)
                return DefaultJsonDataAdapter(nextAdapter, defaultValue)
            }
        }
    }
}