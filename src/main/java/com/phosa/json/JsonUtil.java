package com.phosa.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author phosa.gao
 */
public class JsonUtil {
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parseJsonToObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new LinkedHashMap<>();
        JSONObject object = JSON.parseObject(json);
        for (Map.Entry<String, Object> entry: object.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }
    public static <T> List<T> parseJsonToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
    public static <T> T parseObjectToObject(Object obj, Class<T> clazz) {
        return parseJsonToObject(toJson(obj), clazz);
    }
    public static <T> List<T> parseListToList(List<?> oriList, Class<T> clazz) {
        return parseJsonToList(toJson(oriList), clazz);
    }
    public static JSONObject parseStringToJSONObject(String json) {
        return JSON.parseObject(json);
    }
    public static JSONArray parseStringToJSONArray(String json) {
        return JSON.parseArray(json);
    }
}

