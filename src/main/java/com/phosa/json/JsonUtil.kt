package com.phosa.json

import com.phosa.json.model.JsonArray
import com.phosa.json.model.JsonObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type
import kotlin.collections.toMutableMap

object JsonUtil {

    private val log: Logger = LoggerFactory.getLogger(JsonUtil::class.java)


    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // 获取 moshi 实例的公开 getter
    fun getMoshiInstance(): Moshi {
        return moshi
    }

    // 获取 JsonAdapter
    inline fun <reified T> getAdapter(type: Type): JsonAdapter<T> {
        return getMoshiInstance().adapter(type)
    }


    // 解析 JSON 字符串为指定类型的对象
    inline  fun <reified T> fromJson(json: String, type: Type): T? {
        return try {
            val jsonAdapter = getAdapter<T>(type)
            jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
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


    // 转换任意对象为 JsonAdapter 使用的 JSON 字符串
    fun toJsonString(value: Any): String {
        return try {
            val jsonAdapter = getAdapter<Any>(value.javaClass)
            jsonAdapter.toJson(value)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}




