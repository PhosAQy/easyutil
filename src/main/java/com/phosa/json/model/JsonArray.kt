package com.phosa.json.model

import com.phosa.json.JsonUtil

class JsonArray(private var list: List<Any>) : Iterable<Any> {

    constructor() : this(emptyList())

    // 获取整个列表的元素
    fun getList(): List<Any> {
        return list
    }

    fun size(): Int {
        return list.size
    }

    fun getAsJsonObject(index: Int): JsonObject? {
        return list.getOrNull(index)?.takeIf { it is Map<*, *> }?.let { JsonObject(it as Map<String, Any>) }
    }
    fun getAsJsonArray(index: Int): JsonArray? {
        return list.getOrNull(index)?.takeIf { it is List<*> }?.let { JsonArray(it as List<Any>) }
    }

    fun getAsString(index: Int): String? {
        return list.getOrNull(index)?.toString()
    }

    inline fun <reified T> get(index: Int): T? {
        return getList().getOrNull(index)?.takeIf {it is T}?.let {it as T}
    }

    // 添加或更新一个元素
    fun add(value: Any): Boolean {
        val updatedList = list.toMutableList()  // 将 list 转为 MutableList
        updatedList.add(when (value) {
            is JsonObject -> value.getMap()
            is JsonArray -> value.getList()
            else -> value
        })
        list = updatedList.toList()
        return true
    }
    // 更新指定索引的元素
    fun add(value: Any, index: Int): Any? {
        val oldValue = if (index in list.indices) list[index] else null
        val updatedList = list.toMutableList().apply {
            if (index < size) {
                this[index] = when (value) {
                    is JsonObject -> value.getMap()
                    is JsonArray -> value.getList()
                    else -> value
                }
            } else {
                add(value)
            }
        }
        list = updatedList.toList()
        return oldValue
    }
    // 实现 Iterable 接口的 iterator 方法
    override fun iterator(): Iterator<Any> {
        return list.iterator()
    }

    // 扩展方法，用于打印 list 内容（可选）
    override fun toString(): String {
        return JsonUtil.toJson(getList())
    }
}
