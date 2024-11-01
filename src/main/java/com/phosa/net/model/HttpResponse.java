package com.phosa.net.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * HttpResponse类用于封装HTTP响应的数据。
 * <p>该类包含了响应码、响应体和响应头信息，通过Builder模式来构建实例。
 */
@Getter
public class HttpResponse {
    // HTTP响应码，例如200表示成功，404表示未找到
    private final int code;
    // HTTP响应的内容体
    private final String body;
    // HTTP响应头信息，使用Map来存储，每个键对应多个值
    private final Map<String, List<String>> headers;

    /**
     * 私有构造函数，通过Builder对象创建HttpResponse实例。
     *
     * @param builder 构建HttpResponse的Builder对象
     */
    private HttpResponse(HttpResponse.Builder builder) {
        this.code = builder.code;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    /**
     * 创建一个新的HttpResponse.Builder实例。
     *
     * @return HttpResponse.Builder对象
     */
    public static HttpResponse.Builder newBuilder() {
        return new HttpResponse.Builder();
    }

    /**
     * HttpResponse的Builder类，用于构建HttpResponse对象。
     * <p>通过链式调用设置响应码、响应体和响应头信息。
     */
    public static class Builder {
        // HTTP响应码
        private int code;
        // HTTP响应体
        private String body;
        // HTTP响应头信息
        private Map<String, List<String>> headers;

        /**
         * 设置HTTP响应码。
         *
         * @param code 响应码，例如200、404等
         * @return 当前Builder对象
         */
        public HttpResponse.Builder code(int code) {
            this.code = code;
            return this;
        }

        /**
         * 设置HTTP响应体内容。
         *
         * @param body 响应体的内容
         * @return 当前Builder对象
         */
        public HttpResponse.Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * 设置HTTP响应头信息。
         *
         * @param headers 响应头的键值对集合，每个键对应一个包含多个值的列表
         * @return 当前Builder对象
         */
        public HttpResponse.Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * 构建并返回HttpResponse对象。
         *
         * @return 构建完成的HttpResponse对象
         */
        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
