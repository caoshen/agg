package xyz.dcme.agg.util;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.library.util.LogUtils;

public class AccountUtils {
    private static final String TAG = LogUtils.makeLogTag("AccountUtils");

    public static final String DEFAULT_OAUTH_PROVIDER = "agg";
    public static final String PREF_ACTIVE_ACCOUNT = "chosen_account";
    private static final String PREFIX_PREF_USER_ID = "user_id_";
    private static final String PREFIX_PREF_USER_NAME = "user_name_";
    private static final String PREFIX_PREF_IMAGE_URL = "image_url_";

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean hasActiveAccount(final Context context) {
        return !TextUtils.isEmpty(getActiveAccountName(context));
    }

    public static AccountInfo getActiveAccountInfo(final Context context) {
        AccountInfo info = new AccountInfo();
        String userName = getUserName(context);
        String avatar = getImageUrl(context);
        info.setUserName(userName);
        info.setAvatarUrl(avatar);
        return info;
    }

    public static void setActiveAccountInfo(final Context context, AccountInfo info) {
        String userName = info.getUserName();
        String avatar = info.getAvatarUrl();
        setUserName(context, userName, userName);
        setImageUrl(context, userName, avatar);
        setActiveAccount(context, userName);
    }

    public static String getActiveAccountName(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT, null);
    }

    public static Account getActiveAccount(final Context context) {
        String name = getActiveAccountName(context);
        if (null != name) {
            return new Account(name, AuthUtils.ACCOUNT_TYPE);
        } else {
            return null;
        }
    }

    public static void setActiveAccount(final Context context, final String name) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT, name).apply();
    }

    public static void clearActiveAccount(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().remove(PREF_ACTIVE_ACCOUNT).apply();
    }

    protected static String makeAccountSpecificPrefKey(String name, String prefix) {
        return prefix + name;
    }

    protected static String makeAccountSpecificPrefKey(Context context, String prefix) {
        String name = getActiveAccountName(context);
        return hasActiveAccount(context) ? makeAccountSpecificPrefKey(name, prefix) : null;
    }

    public static void setUserId(final Context context, final String name, final String userId) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(makeAccountSpecificPrefKey(name, PREFIX_PREF_USER_ID), userId).apply();
    }

    public static String getUserId(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String name = makeAccountSpecificPrefKey(context, PREFIX_PREF_USER_ID);
        return hasActiveAccount(context) ? sp.getString(name, null) : null;
    }

    public static void setUserName(final Context context, final String name, final String userName) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(makeAccountSpecificPrefKey(name, PREFIX_PREF_USER_NAME), userName).apply();
    }

    public static String getUserName(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String name = makeAccountSpecificPrefKey(context, PREFIX_PREF_USER_NAME);
        return hasActiveAccount(context) ? sp.getString(name, null) : null;
    }

    public static void setImageUrl(final Context context, final String name, final String imageUrl) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(makeAccountSpecificPrefKey(name, PREFIX_PREF_IMAGE_URL), imageUrl).apply();
    }

    public static String getImageUrl(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String name = makeAccountSpecificPrefKey(context, PREFIX_PREF_IMAGE_URL);
        return hasActiveAccount(context) ? sp.getString(name, null) : null;
    }

    public static boolean isCurrentAccount(Context context, String userName) {
        String accountName = getActiveAccountName(context);
        return !TextUtils.isEmpty(userName) && userName.equals(accountName);
    }

    public static void clearAccount(Context context) {
        setActiveAccount(context, null);
    }
}
