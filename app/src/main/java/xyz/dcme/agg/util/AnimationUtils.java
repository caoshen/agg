package xyz.dcme.agg.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

    public static void showProgress(final View progress, final View content, final boolean active) {
        if (progress == null || content == null) {
            return;
        }
        showSmoothView(progress, active);
        showSmoothView(content, !active);
    }

    private static void showSmoothView(final View view, final boolean active) {
        view.setVisibility(active ? View.VISIBLE : View.GONE);
        view.animate()
                .setDuration(200)
                .alpha(active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(active ? View.VISIBLE : View.GONE);
                    }
                });
    }
}
