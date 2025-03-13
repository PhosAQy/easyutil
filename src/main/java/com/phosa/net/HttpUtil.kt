package com.phosa.net

import com.phosa.json.JsonUtil
import com.phosa.net.model.HttpRequest
import com.phosa.net.model.HttpResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient

/**
 * 一个简单易用的HTTP请求发送工具类，支持GET、POST、PUT、DELETE等请求方法。
 *
 * 该工具类提供了多种方法来发送HTTP请求，并返回响应内容。
 */
object HttpUtil {
    private val log: Logger = LoggerFactory.getLogger(HttpUtil::class.java)
    /**
     * 使用GET请求获取指定URL的响应，并附加请求头。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @return 响应内容
     */
    @JvmOverloads
    fun get(url: String?, headers: Map<String, String>? = null): String? {
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.GET)
            .headers(headers)
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 使用POST请求发送指定URL的请求，附加请求头和参数。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param params 请求参数
     * @return 响应内容
     */
    @JvmOverloads
    fun post(
        url: String?,
        headers: Map<String, String>? = null,
        params: Map<String, String>? = null
    ): String? {
        val body = StringBuilder()
        if (params != null) {
            for (param in params.entries) {
                body.append(param.key).append("=").append(param.value).append("&")
            }
            body.deleteCharAt(body.length - 1)
        }
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.POST)
            .headers(headers)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .body(body.toString())
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 使用POST请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    fun postJson(url: String?, headers: Map<String, String>?, body: String?): String? {
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.POST)
            .headers(headers)
            .header("Content-Type", "application/json")
            .body(body)
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 使用PATCH请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    fun patchJson(url: String?, headers: Map<String, String>?, body: String?): String? {
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.PATCH)
            .headers(headers)
            .header("Content-Type", "application/json")
            .body(body)
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 使用PUT请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    fun putJson(url: String?, headers: Map<String, String>?, body: String?): String? {
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.PUT)
            .headers(headers)
            .header("Content-Type", "application/json")
            .body(body)
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 使用DELETE请求发送指定URL的请求。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @return 响应内容
     */
    fun delete(url: String?, headers: Map<String, String>?): String? {
        val request = HttpRequest.newBuilder()
            .url(url)
            .method(HttpRequest.DELETE)
            .headers(headers)
            .build()
        val response = execute(request)
        return response.body
    }

    /**
     * 执行给定的HTTP请求并返回响应。
     *
     * @param request 要执行的HttpRequest对象
     * @return 执行后的HttpResponse对象
     */
    fun execute(request: HttpRequest): HttpResponse {
        try {
            // 创建HttpClient
            val client = HttpClient.newHttpClient()

            // 创建HttpRequest.Builder
            val builder = java.net.http.HttpRequest.newBuilder()
                .uri(URI(request.url!!))
                .method(
                    request.method,
                    if (request.body != null) java.net.http.HttpRequest.BodyPublishers.ofString(request.body) else java.net.http.HttpRequest.BodyPublishers.noBody()
                )

            // 设置请求头
            if (request.headers.isNotEmpty()) {
                for (header in request.headers.entries) {
                    builder.header(header.key, header.value)
                }
            }

            // 构建HttpRequest
            val httpRequest = builder.build()

            // 发送请求并获取响应
            val httpResponse = client.send<String?>(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString())

            // 创建自定义的HttpResponse对象返回
            return HttpResponse.newBuilder()
                .code(httpResponse.statusCode())
                .body(httpResponse.body())
                .headers(httpResponse.headers().map())
                .build()
        } catch (e: Exception) {
            return HttpResponse.newBuilder().code(500).body(e.message).build()
        }
    }
}
