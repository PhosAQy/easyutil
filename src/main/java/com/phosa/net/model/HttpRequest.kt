package com.phosa.net.model

import lombok.Getter

/**
 * HttpRequest类用于构建HTTP请求的模型。
 *
 * 提供了一种灵活的方式来构建和配置HTTP请求，包括请求方法、URL、请求体和请求头等。
 *
 * 该类是不可变的，使用Builder模式来创建实例。
 *
 * 使用示例：
 * <pre>`HttpRequest request = new HttpRequest.Builder()
 * .url("https://xxxx.com/xxx")
 * .method("POST")
 * .header("Authorization", "Bearer token")
 * .header("Content-Type", "application/json")
 * .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
 * .build();
 * // 执行请求并获取响应
 * HttpResponse response = HttpUtil.execute(request);
 * // 将响应结果打印到控制台
 * System.out.println("Response Code: " + response.getCode());
 * System.out.println("Response Body: " + response.getBody());
 * System.out.println("Response Headers: " + response.getHeaders());
`</pre> *
 */
@Getter
class HttpRequest private constructor(builder: Builder) {


    // HTTP请求的URL
    internal val url: String?

    // HTTP请求的方法，例如GET、POST等
    internal val method: String?

    // HTTP请求的请求体
    internal val body: String?

    // HTTP请求的请求头集合
    internal val headers: MutableMap<String?, String?>

    /**
     * 私有构造函数，用于从Builder对象创建HttpRequest实例。
     *
     * @param builder 构建HttpRequest的Builder对象
     */
    init {
        this.url = builder.url
        this.method = builder.method
        this.body = builder.body
        this.headers = builder.headers
    }

    /**
     * HttpRequest的Builder类，用于构建HttpRequest对象。
     *
     * 通过链式调用设置URL、请求方法、请求体和请求头等。
     */
    class Builder {
        // 请求的URL
        internal var url: String? = null

        // 请求的方法，默认为GET
        internal var method: String? = GET

        // 请求的请求体
        internal var body: String? = null

        // 请求的请求头，使用Map存储键值对
        internal val headers: MutableMap<String?, String?> = HashMap<String?, String?>()

        /**
         * 设置请求的URL。
         *
         * @param url 请求的URL
         * @return 当前Builder对象
         */
        fun url(url: String?): Builder {
            this.url = url
            return this
        }

        /**
         * 设置请求的方法，例如GET、POST等。
         *
         * @param method 请求的方法
         * @return 当前Builder对象
         */
        fun method(method: String?): Builder {
            this.method = method
            return this
        }

        /**
         * 设置请求的请求体。
         *
         * @param body 请求的请求体内容
         * @return 当前Builder对象
         */
        fun body(body: String?): Builder {
            this.body = body
            return this
        }

        /**
         * 添加单个请求头。
         *
         * @param key 请求头的键
         * @param value 请求头的值
         * @return 当前Builder对象
         */
        fun header(key: String, value: String): Builder {
            this.headers.put(key, value)
            return this
        }

        /**
         * 批量添加请求头。
         *
         * @param headers 请求头的键值对集合
         * @return 当前Builder对象
         */
        fun headers(headers: Map<String, String>?): Builder {
            if (headers != null) {
                this.headers.putAll(headers)
            }
            return this
        }

        /**
         * 构建并返回HttpRequest对象。
         *
         * @return 构建完成的HttpRequest对象
         */
        fun build(): HttpRequest {
            return HttpRequest(this)
        }
    }

    companion object {

        // HTTP请求方法的常量
        const val GET: String = "GET"
        const val POST: String = "POST"
        const val PUT: String = "PUT"
        const val DELETE: String = "DELETE"
        const val PATCH: String = "PATCH"
        /**
         * 创建一个新的HttpRequest.Builder实例。
         *
         * @return HttpRequest.Builder对象
         */
        fun newBuilder(): Builder {
            return Builder()
        }
    }
}
