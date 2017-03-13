package xyz.dcme.agg.ui.postdetail;

import android.content.Context;

import java.util.Map;

import xyz.dcme.agg.util.SharedPrefUtils;

class CookieHelper {
    public static Map<String, String> readLoginCookies() {
        return null;
    }

    public static void writeLoginCookies(Context context, Map<String, String> cookies) {
        for (String key : cookies.keySet()) {
            SharedPrefUtils.setPrefString(context, "cookies", key, cookies.get(key));
        }
    }
}
