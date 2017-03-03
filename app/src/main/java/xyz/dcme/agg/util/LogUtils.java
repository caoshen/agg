package xyz.dcme.agg.util;

import android.util.Log;

import xyz.dcme.agg.BuildConfig;

public class LogUtils {
    private static final String LOG_PREFIX = "agg_";
    private static final int LOG_PREFIX_LENGTH   = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH   = 23;

    public static boolean LOGGING_ENABLED = !BuildConfig.BUILD_TYPE.equalsIgnoreCase("release");

    private LogUtils() {

    }

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH  - 1);
        }
        return LOG_PREFIX + str;
    }

    /**
     * Don't use this method when obfuscating class name! Otherwise the tag will be obfuscating.
     * @param clazz
     * @return
     */
    public static String makeLogTag(Class clazz) {
        return makeLogTag(clazz.getSimpleName());
    }

    public static void LOGD(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message);
            }
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message, cause);
            }
        }
    }

    public static void LOGV(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message);
            }
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message, cause);
            }
        }
    }

    public static void LOGI(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.INFO)) {
                Log.i(tag, message);
            }
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.INFO)) {
                Log.i(tag, message, cause);
            }
        }
    }

    public static void LOGW(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.WARN)) {
                Log.w(tag, message);
            }
        }
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.WARN)) {
                Log.w(tag, message, cause);
            }
        }
    }

    public static void LOGE(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.ERROR)) {
                Log.e(tag, message);
            }
        }
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.ERROR)) {
                Log.e(tag, message, cause);
            }
        }
    }

}
