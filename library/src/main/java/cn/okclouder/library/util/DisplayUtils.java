package cn.okclouder.library.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
    private DisplayUtils() {

    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int px = (int) ((dp * metrics.density) + 0.5F);
        return dp > 0 ? px : dp;
    }

    public static int sp2px(Context context, int sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int px = (int) ((sp * metrics.scaledDensity) + 0.5F);
        return sp > 0 ? px : sp;
    }

    public static int px2dp(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dp = (int) ((px / metrics.density) + 0.5F);
        return px > 0 ? dp : px;
    }
}
