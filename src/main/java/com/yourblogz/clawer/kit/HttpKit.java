package com.yourblogz.clawer.kit;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class HttpKit {

    public static class DataKit {
        public static final Map<String, String> data(String ...args) {
            Map<String, String> data = new LinkedHashMap<>();
            if (0 != args.length % 2) {
                return data;
            }
            for (int i = 0; i < args.length; i += 2) {
                data.put(String.valueOf(args[i]), args[i + 1]);
            }
            return data;
        }
    }
    
    public static final Document httpPost(String url) throws IOException {
        return httpPost(url, null);
    }
    
    public static final Document httpPost(String url, Map<String, String> data) throws IOException {
        return getConnection(url, data).post();
    }
    
    public static final Document httpGet(String url) throws IOException {
        return httpGet(url, null);
    }
    
    public static final Document httpGet(String url, Map<String, String> data) throws IOException {
        return getConnection(url, data).get();
    }
    
    private static final Connection getConnection(String url, Map<String, String> data) throws IOException {
        Connection conn = Jsoup.connect(url)
                               .followRedirects(true)
                               .ignoreContentType(true)
                               .timeout(10000)
                               .header("User-Agent", "User-Agent:Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
        
        if (data != null) {
            conn.data(data);
        }
        
        return conn;
    }
}
