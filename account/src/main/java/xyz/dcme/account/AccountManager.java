package xyz.dcme.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import xyz.dcme.account.ui.AccountDummyActivity;

public class AccountManager {

    private static String PREF_ACTIVE_ACCOUNT = "chosen_account";
    private static String PREFIX_PREF_USER_NAME = "user_name_";
    private static String PREFIX_PREF_IMAGE_URL = "image_url_";

    public static boolean hasLoginAccount(Context context) {
        return !TextUtils.isEmpty(getActiveAccountName(context));
    }

    public static void getAccount(Context context, LoginHandler handler) {
        if (hasLoginAccount(context)) {
            handler.onLogin(getActiveAccountInfo(context));
        } else {
            AccountDummyActivity.startLogin(context, handler);
        }
    }

    private static AccountInfo getActiveAccountInfo(Context context) {
        AccountInfo info = new AccountInfo();
        String userName = getUserName(context);
        String avatar = getUserHeaderImageUrl(context);
        info.setUserName(userName);
        info.setAvatarUrl(avatar);
        return info;
    }

    private static String getUserName(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String name = makeAccountSpecificPrefKey(context, PREFIX_PREF_USER_NAME);
        return sp.getString(name, null);
    }

    private static String getUserHeaderImageUrl(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String name = makeAccountSpecificPrefKey(context, PREFIX_PREF_IMAGE_URL);
        return sp.getString(name, null);
    }

    private static String makeAccountSpecificPrefKey(Context context, String prefix) {
        String chosenAccount = getActiveAccountName(context);
        if (TextUtils.isEmpty(chosenAccount)) {
            return null;
        } else {
            return addKeyPrefix(prefix, chosenAccount);
        }
    }

    private static String getActiveAccountName(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT, null);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setActiveAccountInfo(Context context, AccountInfo info) {
        String userName = info.getUserName();
        String avatar = info.getAvatarUrl();
        setUserName(context, userName, userName);
        setImageUrl(context, userName, avatar);
        setActiveAccount(context, userName);
    }

    private static void setUserName(Context context, String name, String userName) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(addKeyPrefix(name, PREFIX_PREF_USER_NAME), userName).apply();
    }

    private static String addKeyPrefix(String name, String prefix) {
        return prefix + name;
    }

    private static void setImageUrl(Context context, String name, String imageUrl) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(addKeyPrefix(name, PREFIX_PREF_IMAGE_URL), imageUrl).apply();
    }

    private static void setActiveAccount(Context context, String name) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT, name).apply();
    }
}
