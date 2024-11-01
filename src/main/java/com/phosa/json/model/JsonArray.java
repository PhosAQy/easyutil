package com.phosa.json.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.phosa.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * JsonArray 类用于封装对 JSONArray 的操作，提供更为便捷的方法来操作 JSON 数组。
 * 包含对数组的添加、删除、查询、排序等多种操作。
 */
@Slf4j
public class JsonArray {
    private final JSONArray jsonArray;

    /**
     * 构造一个空的 JsonArray。
     */
    public JsonArray() {
        jsonArray = new JSONArray();
    }

    /**
     * 使用指定的 JSONArray 构造 JsonArray，如果传入的 JSONArray 为空，则构造一个新的空数组。
     * @param jsonArray 初始化的 JSONArray 对象
     */
    public JsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray != null ? jsonArray : new JSONArray();
    }

    /**
     * 静态工厂方法，用于从一个 JSONArray 对象创建 JsonArray 实例。
     * @param jsonArray 初始化的 JSONArray 对象
     * @return JsonArray 实例
     */
    public static JsonArray from(JSONArray jsonArray) {
        return new JsonArray(jsonArray);
    }

    /**
     * 获取数组的大小。
     * @return 数组中元素的数量
     */
    public int size() {
        return this.jsonArray.size();
    }

    /**
     * 获取指定索引位置的元素。
     * @param index 元素的索引
     * @return 指定索引位置的元素
     */
    public Object get(int index) {
        return this.jsonArray.get(index);
    }

    /**
     * 向数组中添加一个元素。
     * @param value 要添加的元素
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray add(Object value) {
        if (value instanceof JsonObject jo) {
            this.jsonArray.add(jo.getJSONObject());
        } else if (value instanceof JsonArray ja) {
            this.jsonArray.add(ja.getJSONArray());
        } else {
            this.jsonArray.add(value);
        }
        return this;
    }


    /**
     * 向数组中添加一组元素。
     * @param values 要添加的元素集合
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray addAll(Collection<?> values) {
        this.jsonArray.addAll(values);
        return this;
    }

    /**
     * 获取指定索引位置的元素并转换为 JsonArray。
     * @param index 元素的索引
     * @return 对应的 JsonArray 实例
     */
    public JsonArray getAsJsonArray(int index) {
        JSONArray array = this.jsonArray.getJSONArray(index);
        return array != null ? new JsonArray(array) : new JsonArray();
    }

    /**
     * 获取指定索引位置的元素并转换为 JsonObject。
     * @param index 元素的索引
     * @return 对应的 JsonObject 实例
     */
    public JsonObject getAsJsonObject(int index) {
        return new JsonObject(this.jsonArray.getJSONObject(index));
    }

    /**
     * 获取指定索引位置的元素并转换为字符串。
     * @param index 元素的索引
     * @return 对应的字符串值
     */
    public String getAsString(int index) {
        return this.jsonArray.getString(index);
    }

    /**
     * 获取指定索引位置的元素并转换为布尔值。
     * @param index 元素的索引
     * @return 对应的布尔值
     */
    public boolean getAsBoolean(int index) {
        return this.jsonArray.getBooleanValue(index);
    }

    /**
     * 获取指定索引位置的元素并转换为整数。
     * @param index 元素的索引
     * @return 对应的整数值
     */
    public int getAsInt(int index) {
        return this.jsonArray.getIntValue(index);
    }

    /**
     * 获取指定索引位置的元素并转换为双精度浮点数。
     * @param index 元素的索引
     * @return 对应的双精度浮点数值
     */
    public double getAsDouble(int index) {
        return this.jsonArray.getDoubleValue(index);
    }

    /**
     * 获取指定索引位置的元素并转换为长整型。
     * @param index 元素的索引
     * @return 对应的长整型值
     */
    public long getAsLong(int index) {
        return this.jsonArray.getLongValue(index);
    }

    /**
     * 判断数组中是否包含指定元素。
     * @param value 要检查的元素
     * @return 如果包含则返回 true，否则返回 false
     */
    public boolean contains(Object value) {
        return this.jsonArray.contains(value);
    }

    /**
     * 判断当前数组是否包含另一个 JsonArray 的所有元素。
     * @param jsonArray 要检查的 JsonArray
     * @return 如果包含所有元素则返回 true，否则返回 false
     */
    public boolean containsAll(JsonArray jsonArray) {
        return this.jsonArray.containsAll(jsonArray.jsonArray);
    }

    /**
     * 判断当前数组是否包含另一个 JsonArray 的任意一个元素。
     * @param jsonArray 要检查的 JsonArray
     * @return 如果包含任意一个元素则返回 true，否则返回 false
     */
    public boolean containsAny(JsonArray jsonArray) {
        Set<Object> valueSet = new HashSet<>(jsonArray.jsonArray);
        for (Object value : valueSet) {
            if (this.jsonArray.contains(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组是否为空。
     * @return 如果数组为空则返回 true，否则返回 false
     */
    public boolean isEmpty() {
        return this.jsonArray.isEmpty();
    }

    /**
     * 判断数组是否不为空。
     * @return 如果数组不为空则返回 true，否则返回 false
     */
    public boolean isNotEmpty() {
        return !this.jsonArray.isEmpty();
    }

    /**
     * 获取指定元素的索引。
     * @param value 要查找的元素
     * @return 元素的索引，如果不存在则返回 -1
     */
    public int indexOf(Object value) {
        return this.jsonArray.indexOf(value);
    }

    /**
     * 移除指定元素。
     * @param value 要移除的元素
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray remove(Object value) {
        int index = indexOf(value);
        if (index != -1) {
            this.jsonArray.remove(index);
        }
        return this;
    }

    /**
     * 移除数组中的所有指定元素。
     * @param jsonArray 要移除的元素集合
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray removeAll(JsonArray jsonArray) {
        this.jsonArray.removeAll(jsonArray.jsonArray);
        return this;
    }

    /**
     * 保留数组中的所有指定元素，移除其他元素。
     * @param jsonArray 要保留的元素集合
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray retainAll(JsonArray jsonArray) {
        Set<Object> retainSet = new HashSet<>(jsonArray.jsonArray);
        this.jsonArray.removeIf(item -> !retainSet.contains(item));
        return this;
    }

    /**
     * 保留数组中的所有指定元素，移除其他元素。
     * @param values 要保留的元素集合
     * @return 当前 JsonArray 实例，便于链式调用
     */
    public JsonArray retainAll(Collection<?> values) {
        Set<Object> retainSet = new HashSet<>(values);
        this.jsonArray.removeIf(item -> !retainSet.contains(item));
        return this;
    }

    /**
     * 清空数组中的所有元素。
     */
    public void clear() {
        this.jsonArray.clear();
    }

    /**
     * 重置数组，清空所有元素并重新初始化。
     */
    public void reset() {
        this.jsonArray.clear();
        this.jsonArray.addAll(new JSONArray());
    }

    /**
     * 将数组转换为 Object 类型的数组。
     * @return 转换后的数组
     */
    public Object[] toArray() {
        return this.jsonArray.toArray();
    }

    /**
     * 将数组转换为指定类型的数组。
     * @param a 指定类型的数组
     * @param <T> 数组元素类型
     * @return 转换后的数组
     */
    public <T> T[] toArray(T[] a) {
        return this.jsonArray.toArray(a);
    }

    /**
     * 将数组转换为 JSON 字符串。
     * @return 数组的 JSON 字符串表示
     */
    @Override
    public String toString() {
        return this.jsonArray.toJSONString();
    }

    /**
     * 获取内部的 JSONArray 对象。
     * @return 内部的 JSONArray 实例
     */
    public JSONArray getJSONArray() {
        return this.jsonArray;
    }

    /**
     * 对数组进行排序，默认升序。
     */
    public void sort() {
        sort(true);
    }

    /**
     * 对数组进行排序。
     * @param ascending 是否升序排序
     */
    public void sort(boolean ascending) {
        sort(ascending, (String) null);
    }

    /**
     * 根据指定键对数组中的对象进行排序，默认升序。
     * @param key 要排序的键(嵌套键使用点号分隔)
     */
    public void sort(String key) {
        sort(true, key);
    }

    /**
     * 根据指定键对数组中的对象进行排序。
     * @param key 要排序的键(嵌套键使用点号分隔)
     * @param ascending 是否升序排序
     */
    public void sort(boolean ascending, String... key) {

        List<Object> jsonValues = new ArrayList<>(this.jsonArray);
        jsonValues.sort((a, b) -> {
            try {
                String valA = key[0] == null ? a.toString() : (new JsonObject((JSONObject)a)).getNestedValue(key);
                String valB = key[0] == null ? b.toString() : (new JsonObject((JSONObject)a)).getNestedValue(key);
                return ascending ? valA.compareTo(valB) : valB.compareTo(valA);
            } catch (JSONException e) {
                log.error("Error while sorting JsonArray: {}", e.getMessage(), e);
                return 0;
            }
        });
        this.jsonArray.clear();
        this.jsonArray.addAll(jsonValues);
    }

}
