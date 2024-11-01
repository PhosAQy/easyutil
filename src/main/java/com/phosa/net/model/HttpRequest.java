package com.phosa.net.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpRequest类用于构建HTTP请求的模型。
 * <p>提供了一种灵活的方式来构建和配置HTTP请求，包括请求方法、URL、请求体和请求头等。
 * <p>该类是不可变的，使用Builder模式来创建实例。
 * <p>使用示例：
 * <pre>{@code
 *     HttpRequest request = new HttpRequest.Builder()
 *                     .url("https://xxxx.com/xxx")
 *                     .method("POST")
 *                     .header("Authorization", "Bearer token")
 *                     .header("Content-Type", "application/json")
 *                     .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
 *                     .build();
 *     // 执行请求并获取响应
 *     HttpResponse response = HttpUtil.execute(request);
 *     // 将响应结果打印到控制台
 *     System.out.println("Response Code: " + response.getCode());
 *     System.out.println("Response Body: " + response.getBody());
 *     System.out.println("Response Headers: " + response.getHeaders());
 * }</pre>
 */
@Getter
public class HttpRequest {
    // HTTP请求方法的常量
    public final String GET = "GET";
    public final String POST = "POST";
    public final String PUT = "PUT";
    public final String DELETE = "DELETE";
    public final String PATCH = "PATCH";

    // HTTP请求的URL
    private final String url;
    // HTTP请求的方法，例如GET、POST等
    private final String method;
    // HTTP请求的请求体
    private final String body;
    // HTTP请求的请求头集合
    private final Map<String, String> headers;

    /**
     * 私有构造函数，用于从Builder对象创建HttpRequest实例。
     *
     * @param builder 构建HttpRequest的Builder对象
     */
    private HttpRequest(HttpRequest.Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    /**
     * 创建一个新的HttpRequest.Builder实例。
     *
     * @return HttpRequest.Builder对象
     */
    public static HttpRequest.Builder newBuilder() {
        return new HttpRequest.Builder();
    }

    /**
     * HttpRequest的Builder类，用于构建HttpRequest对象。
     * <p>通过链式调用设置URL、请求方法、请求体和请求头等。
     */
    public static class Builder {
        // 请求的URL
        private String url;
        // 请求的方法，默认为GET
        private String method = "GET";
        // 请求的请求体
        private String body;
        // 请求的请求头，使用Map存储键值对
        private final Map<String, String> headers = new HashMap<>();

        /**
         * 设置请求的URL。
         *
         * @param url 请求的URL
         * @return 当前Builder对象
         */
        public HttpRequest.Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置请求的方法，例如GET、POST等。
         *
         * @param method 请求的方法
         * @return 当前Builder对象
         */
        public HttpRequest.Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * 设置请求的请求体。
         *
         * @param body 请求的请求体内容
         * @return 当前Builder对象
         */
        public HttpRequest.Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * 添加单个请求头。
         *
         * @param key 请求头的键
         * @param value 请求头的值
         * @return 当前Builder对象
         */
        public HttpRequest.Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        /**
         * 批量添加请求头。
         *
         * @param headers 请求头的键值对集合
         * @return 当前Builder对象
         */
        public HttpRequest.Builder headers(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        /**
         * 构建并返回HttpRequest对象。
         *
         * @return 构建完成的HttpRequest对象
         */
        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
