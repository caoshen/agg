package xyz.dcme.agg.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class AnimationUtils {

    private static final int DURATION = 200;

    private AnimationUtils() {

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
                .setDuration(DURATION)
                .alpha(active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(active ? View.VISIBLE : View.GONE);
                    }
                });
    }
}
