package com.phosa.net.model

import lombok.Getter

/**
 * HttpResponse类用于封装HTTP响应的数据。
 *
 * 该类包含了响应码、响应体和响应头信息，通过Builder模式来构建实例。
 */
@Getter
class HttpResponse private constructor(builder: Builder) {
    // HTTP响应码，例如200表示成功，404表示未找到
    internal val code: Int

    // HTTP响应的内容体
    internal val body: String?

    // HTTP响应头信息，使用Map来存储，每个键对应多个值
    internal val headers: MutableMap<String?, MutableList<String?>?>?

    /**
     * 私有构造函数，通过Builder对象创建HttpResponse实例。
     *
     * @param builder 构建HttpResponse的Builder对象
     */
    init {
        this.code = builder.code
        this.body = builder.body
        this.headers = builder.headers
    }

    /**
     * HttpResponse的Builder类，用于构建HttpResponse对象。
     *
     * 通过链式调用设置响应码、响应体和响应头信息。
     */
    class Builder {
        // HTTP响应码
        internal var code = 0

        // HTTP响应体
        internal var body: String? = null

        // HTTP响应头信息
        internal var headers: MutableMap<String?, MutableList<String?>?>? = null

        /**
         * 设置HTTP响应码。
         *
         * @param code 响应码，例如200、404等
         * @return 当前Builder对象
         */
        fun code(code: Int): Builder {
            this.code = code
            return this
        }

        /**
         * 设置HTTP响应体内容。
         *
         * @param body 响应体的内容
         * @return 当前Builder对象
         */
        fun body(body: String?): Builder {
            this.body = body
            return this
        }

        /**
         * 设置HTTP响应头信息。
         *
         * @param headers 响应头的键值对集合，每个键对应一个包含多个值的列表
         * @return 当前Builder对象
         */
        fun headers(headers: MutableMap<String?, MutableList<String?>?>?): Builder {
            this.headers = headers
            return this
        }

        /**
         * 构建并返回HttpResponse对象。
         *
         * @return 构建完成的HttpResponse对象
         */
        fun build(): HttpResponse {
            return HttpResponse(this)
        }
    }

    companion object {
        /**
         * 创建一个新的HttpResponse.Builder实例。
         *
         * @return HttpResponse.Builder对象
         */
        fun newBuilder(): Builder {
            return Builder()
        }
    }
}
