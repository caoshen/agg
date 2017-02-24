package xyz.dcme.agg.util;

import android.content.Context;

public class HttpUtils {
    private static HttpUtils sInstance;

    public HttpUtils(Context context) {

    }

    public static HttpUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (HttpUtils.class) {
                if (sInstance == null) {
                    sInstance = new HttpUtils(context);
                }
            }
        }
        return sInstance;
    }
}
