package xyz.dcme.agg.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {

    private static final String ACCOUNT = "account";
    private static final String ACCOUNT_USERNAME = "account_username";
    private static final String EMPTY_STR = "";
    private static final String ACCOUNT_AVATAR = "account_password";

    public static String getPrefString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void setPrefString(Context context, String pref, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getUserName(Context context) {
        return getPrefString(context, ACCOUNT_USERNAME, EMPTY_STR);
    }

    public static String getAvatar(Context context) {
        return getPrefString(context, ACCOUNT_AVATAR, EMPTY_STR);
    }

    public static void setUserName(Context context, String userName) {
        setPrefString(context, ACCOUNT_USERNAME, userName);
    }

    public static void setAvatar(Context context, String password) {
        setPrefString(context, ACCOUNT_AVATAR, password);
    }
}
