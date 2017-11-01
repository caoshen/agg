package xyz.dcme.agg.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

public class VersionUtil {
    private static final String TAG = "VersionUtil";

    public static String getAppVersionName(Context context) {
        if (context == null) {
            Log.e(TAG, "getAppVersionName -> null context");
            return "";
        }
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getAppVersionName -> " + e);
        }
        return versionName;
    }
}
