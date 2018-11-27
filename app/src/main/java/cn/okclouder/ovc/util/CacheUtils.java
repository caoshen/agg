package cn.okclouder.ovc.util;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class CacheUtils {
    private static final String TAG = "CacheUtils";
    public static void getCacheSize(Context context) {
        long size = 0;
        File filesDir = context.getFilesDir();
        Log.d(TAG, "files dir:" + filesDir.getAbsolutePath());
        File cacheDir = context.getCacheDir();
        Log.d(TAG, "cache dir:" + cacheDir.getAbsolutePath());

    }
}
