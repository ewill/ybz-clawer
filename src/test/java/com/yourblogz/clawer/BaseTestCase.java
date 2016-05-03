package com.yourblogz.clawer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
    
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
    
    protected final String httpPost(String url) throws IOException {
        return httpPost(url, null);
    }
    
    protected final String httpPost(String url, Map<String, String> data) throws IOException {
        return httpSender(Method.POST, url, data);
    }
    
    protected final String httpGet(String url) throws IOException {
        return httpGet(url, null);
    }
    
    protected final String httpGet(String url, Map<String, String> data) throws IOException {
        return httpSender(Method.GET, url, data);
    }
    
    private final String httpSender(Method method, String url, Map<String, String> data) throws IOException {
        Connection conn = Jsoup.connect(url)
                               .method(method)
                               .followRedirects(true)
                               .ignoreContentType(true);
        
        if (data != null) {
            conn.data(data);
        }
        
        Response response = conn.execute();
        if (response.statusCode() == 200) {
            return response.body();
        }
        
        return null;
    }
}
