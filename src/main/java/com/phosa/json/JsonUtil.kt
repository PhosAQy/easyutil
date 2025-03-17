package com.phosa.json

import com.phosa.json.model.JsonArray
import com.phosa.json.model.JsonObject
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.ArrayList
import kotlin.collections.toMutableMap

object JsonUtil {

    private val log: Logger = LoggerFactory.getLogger(JsonUtil::class.java)


    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(LinkedHashMapJsonAdapter.FACTORY)
        .add(ArrayListJsonAdapter.FACTORY)
        .build()

    // 获取 moshi 实例的公开 getter
    fun getMoshiInstance(): Moshi {
        return moshi
    }

    // 获取 JsonAdapter
    fun <T> getAdapter(type: Type): JsonAdapter<T> {
        return getMoshiInstance().adapter(type)
    }


    // 解析 JSON 字符串为指定类型的对象
    fun <T> fromJson(json: String, type: Type): T? {
        return try {
            val jsonAdapter = getAdapter<T>(type)
            jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    // 解析 JSON 字符串为 Map
    fun parseStringToJsonObject(json: String): JsonObject? {
        return JsonObject(parseStringToMap(json)?:emptyMap())
    }

    // 解析 JSON 字符串为 Map
    fun parseStringToJsonArray(json: String): JsonArray? {
        return JsonArray(
            try {
                val jsonAdapter = getAdapter<List<Any>>(List::class.java)
                jsonAdapter.fromJson(json) ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        )
    }
    // 解析 JSON 字符串为 Map
    fun parseStringToMap(json: String): Map<String, Any>? {
        return fromJson<Map<String, Any>>(json, Map::class.java)
    }
    fun <T> parseObjectToObject(json: Any, clazz: Class<T>): T? {
        return fromJson(toJson(json), clazz)
    }

    fun <T> parseStringToObject(json: String, clazz: Class<T>): T? {
        return fromJson(json, clazz)
    }

    inline fun <reified T> parseObjectToObject(json: Any) : T? {
        return fromJson<T>(toJson(json), T::class.java)
    }
    inline fun <reified T> parseStringToObject(json: String) : T? {
        return fromJson<T>(json, T::class.java)
    }
    fun <T> parseStringToList(json: String) : List<T>? {
        return fromJson<List<T>>(json, List::class.java)
    }
    fun <T> parseListToList(list: List<Any>) : List<T>? {
        return fromJson<List<T>>(toJson(list), List::class.java)
    }


    // 将对象转为 JSON 字符串
    fun toJson(obj: Any): String {
        return try {
            val jsonAdapter = getAdapter<Any>(obj.javaClass)
            jsonAdapter.toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    class LinkedHashMapJsonAdapter<K, V>(moshi: Moshi, keyType: Type?, valueType: Type?) :
        JsonAdapter<java.util.LinkedHashMap<K?, V?>?>() {
        private val keyAdapter: JsonAdapter<K>
        private val valueAdapter: JsonAdapter<V>

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: java.util.LinkedHashMap<K?, V?>?) {
            writer.beginObject()
            for (entry: Map.Entry<K?, V?> in value!!.entries) {
                if (entry.key == null) {
                    throw JsonDataException("Map key is null at " + writer.path)
                }
                writer.promoteValueToName()
                keyAdapter.toJson(writer, entry.key)
                valueAdapter.toJson(writer, entry.value)
            }
            writer.endObject()
        }

        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): java.util.LinkedHashMap<K?, V?> {
            val result = LinkedHashMap<K?, V?>()
            reader.beginObject()
            while (reader.hasNext()) {
                reader.promoteNameToValue()
                val name = keyAdapter.fromJson(reader)
                val value = valueAdapter.fromJson(reader)
                val replaced = result.put(name, value)
                if (replaced != null) {
                    throw JsonDataException(
                        "Map key '"
                                + name
                                + "' has multiple values at path "
                                + reader.path
                                + ": "
                                + replaced
                                + " and "
                                + value
                    )
                }
            }
            reader.endObject()
            return result
        }

        override fun toString(): String {
            return "JsonAdapter($keyAdapter=$valueAdapter)"
        }

        companion object {
            val FACTORY: Factory =
                Factory { type, annotations, moshi ->
                    val rawType = Types.getRawType(type)
                    if (annotations.isNotEmpty()) return@Factory null
                    if (rawType != java.util.LinkedHashMap::class.java) return@Factory null
                    val keyAndValue = if (type === java.util.Properties::class.java) arrayOf<Type>(
                        String::class.java,
                        String::class.java
                    ) else {
                        arrayOf<Type>(Any::class.java, Any::class.java)
                    }
                    LinkedHashMapJsonAdapter<Any?, Any>(
                        moshi,
                        keyAndValue[0],
                        keyAndValue[1]
                    ).nullSafe()
                }
        }

        init {
            keyAdapter = moshi.adapter(keyType)
            valueAdapter = moshi.adapter(valueType)
        }
    }
    class ArrayListJsonAdapter<V>(moshi: Moshi, valueType: Type?) :
        JsonAdapter<java.util.ArrayList<V?>?>() {
        private val valueAdapter: JsonAdapter<V>

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: java.util.ArrayList<V?>?) {
            writer.beginObject()
            for (entry: V? in value!!) {
                if (entry == null) {
                    throw JsonDataException("List value is null at " + writer.path)
                }
                writer.promoteValueToName()
                valueAdapter.toJson(writer, entry)
            }
            writer.endObject()
        }

        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): java.util.ArrayList<V?> {
            val result = ArrayList<V?>()
            reader.beginObject()
            while (reader.hasNext()) {
                reader.promoteNameToValue()
                val value = valueAdapter.fromJson(reader)
                result.add(value)
            }
            reader.endObject()
            return result
        }

        override fun toString(): String {
            return "JsonAdapter($valueAdapter)"
        }

        companion object {
            val FACTORY: Factory =
                Factory { type, annotations, moshi ->
                    val rawType = Types.getRawType(type)
                    if (annotations.isNotEmpty()) return@Factory null
                    if (rawType != java.util.LinkedHashMap::class.java) return@Factory null
                    val value =
                        if (type === java.util.Properties::class.java)
                            String::class.java
                        else {
                            Any::class.java
                        }
                    ArrayListJsonAdapter<Any>(
                        moshi,
                        value
                    ).nullSafe()
                }
        }

        init {
            valueAdapter = moshi.adapter(valueType)
        }
    }
}




