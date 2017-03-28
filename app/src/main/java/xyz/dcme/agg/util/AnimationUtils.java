package xyz.dcme.agg.util;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class AnimationUtils {

    private AnimationUtils() {

    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation animation = visibility == View.VISIBLE
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        v.startAnimation(animation);
    }
}
