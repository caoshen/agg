package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CookieHelper {
    private static final String COOKIES = "cookies";
    private static final String EMPTY_STRING = "";

    private static SharedPreferences getSharedPreferences(final Context context) {
        return context.getSharedPreferences(COOKIES, Context.MODE_PRIVATE);
    }

    public static Map<String, String> getCookies(Context context, List<String> keys) {
        Map<String, String> cookies = new HashMap<>();
        for (String key : keys) {
            String value = getCookie(context, key);
            cookies.put(key, value);
        }
        return cookies;
    }

    public static void putCookies(Context context, Map<String, String> cookies) {
       for (String key : cookies.keySet()) {
           putCookie(context, key, cookies.get(key));
       }
    }

    public static String getCookie(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, EMPTY_STRING);
    }

    public static void putCookie(Context context, String key, String value) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(key, value).apply();
    }

}
