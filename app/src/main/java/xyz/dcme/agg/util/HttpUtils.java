package xyz.dcme.agg.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

public class HttpUtils {
    private static HttpUtils sInstance;

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (sInstance == null) {
            synchronized (HttpUtils.class) {
                if (sInstance == null) {
                    sInstance = new HttpUtils();
                }
            }
        }
        return sInstance;
    }

    public void get(String url, StringCallback callback) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(callback);
    }

    public void post(String url, Map<String, String> headers, Map<String, String> params, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .headers(headers)
                .params(params)
                .build()
                .execute(callback);
    }
}
