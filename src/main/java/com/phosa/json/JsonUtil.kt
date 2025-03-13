package com.phosa.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.phosa.json.model.JsonArray;
import com.phosa.json.model.JsonObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类，用于处理对象与JSON之间的转换。
 * <p>该工具类提供了多种方法来将Java对象、列表、Map等转换为JSON格式，或者将JSON字符串转换为对应的Java对象。
 */
public class JsonUtil {

    /**
     * 将对象转换为JSON字符串。
     *
     * @param obj 需要转换为JSON的对象
     * @return JSON格式的字符串
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将JSON字符串解析为指定类型的对象。
     *
     * @param json JSON字符串
     * @param clazz 目标对象的类型
     * @param <T> 泛型类型
     * @return 解析后的对象
     */
    public static <T> T parseStringToObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz, JSONReader.Feature.SupportSmartMatch);
    }

    /**
     * 将JSON字符串解析为Map，键和值均为字符串类型。
     *
     * @param json JSON字符串
     * @return 包含JSON内容的Map对象
     */
    public static Map<String, String> parseStringToMap(String json) {
        Map<String, String> map = new LinkedHashMap<>();
        JsonObject object = parseStringToJSONObject(json);
        for (Map.Entry<String, Object> entry : object.getEntrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * 将JSON字符串解析为指定类型的列表。
     *
     * @param json JSON字符串
     * @param clazz 列表中元素的类型
     * @param <T> 泛型类型
     * @return 解析后的列表
     */
    public static <T> List<T> parseStringToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 将一个对象转换为另一个指定类型的对象。
     *
     * @param obj 原始对象
     * @param clazz 目标对象的类型
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T parseObjectToObject(Object obj, Class<T> clazz) {
        return parseStringToObject(toJson(obj), clazz);
    }

    /**
     * 将一个列表转换为另一个指定类型的列表。
     *
     * @param oriList 原始列表
     * @param clazz 目标列表中元素的类型
     * @param <T> 泛型类型
     * @return 转换后的列表
     */
    public static <T> List<T> parseListToList(List<?> oriList, Class<T> clazz) {
        return parseStringToList(toJson(oriList), clazz);
    }

    /**
     * 将JSON字符串解析为JSONObject对象。
     *
     * @param json JSON字符串
     * @return JSONObject对象
     */
    public static JsonObject parseStringToJSONObject(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return new JsonObject(jsonObject);
    }

    /**
     * 将JSON字符串解析为JSONArray对象。
     *
     * @param json JSON字符串
     * @return JSONArray对象
     */
    public static JsonArray parseStringToJSONArray(String json) {
        return new JsonArray(JSON.parseArray(json));
    }
}
