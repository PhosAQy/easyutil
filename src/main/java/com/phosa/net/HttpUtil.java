package com.phosa.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个简单易用的Http请求发送工具类，支持GET、POST、PUT、DELETE等请求方法
 *
 * <p>
 *     使用示例：
 * <pre>{@code
 *     Request request = new Request.Builder()
 *                     .url("https://xxxx.com/xxx")
 *                     .method("POST")
 *                     .header("Authorization", "Bearer token")
 *                     .header("Content-Type", "application/json")
 *                     .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
 *                     .build();
 *     // Execute the request and get the response
 *     Response response = execute(request);
 *     // Print the response details to the console
 *     System.out.println("Response Code: " + response.getCode());
 *     System.out.println("Response Body: " + response.getBody());
 *     System.out.println("Response Headers: " + response.getHeaders());
 * }</pre>
 */
public class HttpUtil {

    // 请求体封装类
    public static class Request {
        private final String url;
        private final String method;
        private final String body;
        private final Map<String, String> headers;

        private Request(Builder builder) {
            this.url = builder.url;
            this.method = builder.method;
            this.body = builder.body;
            this.headers = builder.headers;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public String getBody() {
            return body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        // Builder class for Request
        public static class Builder {
            private String url;
            private String method = "GET";
            private String body;
            private final Map<String, String> headers = new HashMap<>();

            public Builder url(String url) {
                this.url = url;
                return this;
            }

            public Builder method(String method) {
                this.method = method;
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder header(String key, String value) {
                this.headers.put(key, value);
                return this;
            }

            public Builder headers(Map<String, String> headers) {
                this.headers.putAll(headers);
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }

    public static Response execute(Request request) throws Exception {
        URL url = new URL(request.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getMethod());

        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        if (request.getBody() != null && ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()))) {
            connection.setDoOutput(true); // Enable output for the connection
            try (OutputStream os = connection.getOutputStream()) {
                os.write(request.getBody().getBytes()); // Write the body content
                os.flush(); // Flush the output stream to ensure all data is sent
            }
        }

        int responseCode = connection.getResponseCode();
        StringBuilder responseContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ? connection.getInputStream() : connection.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
        }

        return new Response.Builder()
                .code(responseCode)
                .body(responseContent.toString())
                .headers(connection.getHeaderFields())
                .build();
    }

    public static class Response {
        private final int code;
        private final String body;
        private final Map<String, List<String>> headers;

        private Response(Builder builder) {
            this.code = builder.code;
            this.body = builder.body;
            this.headers = builder.headers;
        }

        public int getCode() {
            return code;
        }

        public String getBody() {
            return body;
        }

        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        // Builder class for Response
        public static class Builder {
            private int code;
            private String body;
            private Map<String, List<String>> headers;

            public Builder code(int code) {
                this.code = code;
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder headers(Map<String, List<String>> headers) {
                this.headers = headers;
                return this;
            }

            public Response build() {
                return new Response(this);
            }
        }
    }

    // Main method for testing the HttpUtil class
    public static void main(String[] args) {
        try {
            // Create a new Request instance using the Builder pattern
            Request request = new Request.Builder()
                    .url("https://jsonplaceholder.typicode.com/posts")
                    .method("POST")
                    .header("Authorization", "Bearer token")
                    .header("Content-Type", "application/json")
                    .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
                    .build();

            // Execute the request and get the response
            Response response = execute(request);
            // Print the response details to the console
            System.out.println("Response Code: " + response.getCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (Exception e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
    }
}
