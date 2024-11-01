package com.phosa.net;

import com.phosa.net.model.HttpRequest;
import com.phosa.net.model.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.Map;

/**
 * 一个简单易用的HTTP请求发送工具类，支持GET、POST、PUT、DELETE等请求方法。
 * <p>该工具类提供了多种方法来发送HTTP请求，并返回响应内容。
 */
@Slf4j
public class HttpUtil {

    /**
     * 使用GET请求获取指定URL的响应。
     *
     * @param url 请求的URL
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 使用GET请求获取指定URL的响应，并附加请求头。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers) {
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("GET")
                .headers(headers)
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 使用POST请求发送指定URL的请求。
     *
     * @param url 请求的URL
     * @return 响应内容
     */
    public static String post(String url) {
        return post(url, null, null);
    }

    /**
     * 使用POST请求发送指定URL的请求，并附加参数。
     *
     * @param url 请求的URL
     * @param params 请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }

    /**
     * 使用POST请求发送指定URL的请求，附加请求头和参数。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param params 请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        StringBuilder body = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                body.append(param.getKey()).append("=").append(param.getValue()).append("&");
            }
            body.deleteCharAt(body.length() - 1);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("POST")
                .headers(headers)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(body.toString())
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 使用POST请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    public static String postJson(String url, Map<String, String> headers, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("POST")
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(body)
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 使用PATCH请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    public static String patchJson(String url, Map<String, String> headers, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("PATCH")
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(body)
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 使用PUT请求发送JSON数据。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param body 请求体内容
     * @return 响应内容
     */
    public static String putJson(String url, Map<String, String> headers, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("PUT")
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(body)
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 使用DELETE请求发送指定URL的请求。
     *
     * @param url 请求的URL
     * @param headers 请求头信息
     * @return 响应内容
     */
    public static String delete(String url, Map<String, String> headers) {
        HttpRequest request = HttpRequest.newBuilder()
                .url(url)
                .method("DELETE")
                .headers(headers)
                .build();
        HttpResponse response = execute(request);
        return response.getBody();
    }

    /**
     * 执行给定的HTTP请求并返回响应。
     *
     * @param request 要执行的HttpRequest对象
     * @return 执行后的HttpResponse对象
     */
    public static HttpResponse execute(HttpRequest request) {
        try {
            // 创建HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // 创建HttpRequest.Builder
            java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder()
                    .uri(new URI(request.getUrl()))
                    .method(request.getMethod(), request.getBody() != null ?
                            java.net.http.HttpRequest.BodyPublishers.ofString(request.getBody()) : java.net.http.HttpRequest.BodyPublishers.noBody());

            // 设置请求头
            if (request.getHeaders() != null) {
                for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                    builder.header(header.getKey(), header.getValue());
                }
            }

            // 构建HttpRequest
            java.net.http.HttpRequest httpRequest = builder.build();

            // 发送请求并获取响应
            java.net.http.HttpResponse<String> httpResponse = client.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());

            // 创建自定义的HttpResponse对象返回
            return HttpResponse.newBuilder()
                    .code(httpResponse.statusCode())
                    .body(httpResponse.body())
                    .headers(httpResponse.headers().map())
                    .build();
        } catch (Exception e) {
            // 错误处理，返回包含错误信息的响应
            log.error("请求执行失败", e);
            return HttpResponse.newBuilder().code(500).body(e.getMessage()).build();
        }
    }

}
