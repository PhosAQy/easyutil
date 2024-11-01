package com.phosa.json.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.phosa.json.exception.JsonException;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class JsonObject {
    JSONObject jsonObject;
    public JsonObject() {
        jsonObject = new JSONObject();
    }
    public JsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int size() {
        return jsonObject.size();
    }

    public boolean containsKey(String key) {
        return jsonObject.containsKey(key);
    }

    public Object put(String key, Object value) {

        if (value instanceof JsonObject) {
            return this.jsonObject.put(key, ((JsonObject) value).getJSONObject());
        } else if (value instanceof JsonArray) {
            return this.jsonObject.put(key, ((JsonArray) value).getJSONArray());
        }
        return jsonObject.put(key, value);
    }

    public JsonObject fluentPut(String key, Object value) {
        put(key, value);
        return this;
    }

    public Object get(String key) {
        return jsonObject.get(key);
    }

    public String getAsString(String key) {
        return jsonObject.getString(key);
    }

    public JsonObject getAsJsonObject(String key) {
        return new JsonObject(jsonObject.getJSONObject(key));
    }
    public JsonArray getAsJsonArray(String key) {
        return new JsonArray(jsonObject.getJSONArray(key));
    }
    public boolean getAsBoolean(String key) {
        return jsonObject.getBoolean(key);
    }
    public int getAsInt(String key) {
        return jsonObject.getIntValue(key);
    }
    public double getAsDouble(String key) {
        return jsonObject.getDoubleValue(key);
    }
    public long getAsLong(String key) {
        return jsonObject.getLongValue(key);
    }
    public Set<Map.Entry<String, Object>> getEntrySet() {
        return jsonObject.entrySet();
    }
    public boolean containsValue(Object value) {
        return jsonObject.containsValue(value);
    }
    public boolean containsKey(Object key) {
        return jsonObject.containsKey(key);
    }
    public boolean containsAll(JsonObject jsonObject) {
        return this.getEntrySet().containsAll(jsonObject.getEntrySet());
    }
    public boolean containsAll(Object[] keys) {
        for (Object key : keys) {
            if (!jsonObject.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAny(JsonObject jsonObject) {
        for (Object key : jsonObject.jsonObject.keySet()) {
            if (this.jsonObject.containsKey(key)) {
                return true;
            }
        }
        return false;
    }
    public boolean containsAny(Object[] keys) {
        for (Object key : keys) {
            if (jsonObject.containsKey(key)) {
                return true;
            }
        }
        return false;
    }
    public String getNestedValue(String... keys) {
        JsonObject current = this;
        for (String k : keys) {
            if (current != null) {
                current = current.getAsJsonObject(k);
            } else {
                throw new JsonException("Invalid key path: " + Arrays.toString(keys));
            }
        }
        return current != null ? current.toString() : "";
    }
    public String toString() {
        return JSON.toJSONString(this.jsonObject);
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }
}
