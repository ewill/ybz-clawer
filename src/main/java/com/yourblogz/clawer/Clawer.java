package com.yourblogz.clawer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class Clawer extends Thread {

    protected static class DataKit {
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
    
    protected final Document httpPost(String url) throws IOException {
        return httpPost(url, null);
    }
    
    protected final Document httpPost(String url, Map<String, String> data) throws IOException {
        return getConnection(url, data).post();
    }
    
    protected final Document httpGet(String url) throws IOException {
        return httpGet(url, null);
    }
    
    protected final Document httpGet(String url, Map<String, String> data) throws IOException {
        return getConnection(url, data).get();
    }
    
    private final Connection getConnection(String url, Map<String, String> data) throws IOException {
        Connection conn = Jsoup.connect(url)
                               .followRedirects(true)
                               .ignoreContentType(true)
                               .timeout(10000);
        
        if (data != null) {
            conn.data(data);
        }
        
        return conn;
    }
}
