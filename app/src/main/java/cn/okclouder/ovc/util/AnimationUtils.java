package cn.okclouder.ovc.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class AnimationUtils {

    private static final long DURATION = 200L;

    private AnimationUtils() {

    }

    public static void showProgress(final View progress, final View content, final boolean active) {
        if (progress == null || content == null) {
            return;
        }

        if (active) {
            showView(progress, content);
        } else {
            showView(content, progress);
        }
    }

    private static void showView(final View showView, final View hideView) {
        showView.setVisibility(View.VISIBLE);
        showView.animate()
                .setDuration(DURATION)
                .alpha(1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showView.setVisibility(View.VISIBLE);
                    }
                });

        hideView.setVisibility(View.GONE);
        hideView.animate()
                .setDuration(DURATION)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
    }
}
