package com.phosa.json.model

import com.phosa.json.JsonUtil

class JsonObject(private var map: Map<String, Any>) {

    constructor() : this(emptyMap())

    fun getMap(): Map<String, Any> {
        return map
    }

    fun getAsBoolean(key: String): Boolean? {
        return get<Boolean>(key)
    }
    fun getAsInt(key: String): Int? {
        return get<Int>(key)
    }
    fun getAsString(key: String): String? {
        return get<String>(key)
    }
    fun getAsJsonObject(key: String): JsonObject? {
        return JsonObject(get<Map<String, Any>>(key)?:emptyMap())
    }

    fun getAsJsonArray(key: String): JsonArray? {
        return JsonArray(get<List<Any>>(key)?:emptyList())
    }

    inline fun <reified T> get(key: String): T? {
        return getMap()[key]?.takeIf { it is T }?.let { it as T }
    }

    fun containsKey(key: String): Boolean {
        return getMap().containsKey(key)
    }
    fun containsValue(value: Any): Boolean {
        return getMap().containsValue(value)
    }

    // 添加或更新一个键值对
    fun put(key: String, value: Any): Any? {
        val oldValue = map[key]
        val updatedMap = map.toMutableMap().apply {
            this[key] = when (value) {
                is JsonObject -> value.getMap()
                is JsonArray -> value.getList()
                else -> value
            }
        }
        map = updatedMap.toMap()
        return oldValue
    }

    // 扩展方法，用于打印 map 内容（可选）
    override fun toString(): String {
        return JsonUtil.toJson(getMap())
    }


}
