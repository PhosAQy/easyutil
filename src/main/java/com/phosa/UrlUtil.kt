package com.phosa

import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

/**
 * URL工具类
 */
object UrlUtil {
    /**
     * 将字符串url转换为URI对象
     * @param strUrl 字符串url
     * @return URI对象
     * @throws MalformedURLException 格式错误的 URL
     * @throws URISyntaxException 表明无法将字符串解析为 URI 引用
     */
    @Throws(MalformedURLException::class, URISyntaxException::class)
    fun toUri(strUrl: String): URI {
        val url = URL(strUrl)
        return URI(url.protocol, null, url.host, url.port, url.path, url.query, null)
    }
}
