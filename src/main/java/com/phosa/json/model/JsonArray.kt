package com.phosa.json.model

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

    // 添加或更新一个键值对
    fun add(value: Any): Boolean {
        val updatedList = list.toMutableList()  // 将 list 转为 MutableList
        return updatedList.add(value).also { if (it) list = updatedList }
    }

    // 实现 Iterable 接口的 iterator 方法
    override fun iterator(): Iterator<Any> {
        return list.iterator()
    }
}
