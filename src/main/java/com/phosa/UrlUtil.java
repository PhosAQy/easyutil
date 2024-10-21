package com.phosa;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author phosa.gao
 */
public class UrlUtil {

    public static URI toUri(String strUrl) throws MalformedURLException, URISyntaxException {

        URL url = new URL(strUrl);
        return new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
    }
}
