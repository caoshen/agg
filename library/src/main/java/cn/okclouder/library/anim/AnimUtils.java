package cn.okclouder.library.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;

public class AnimUtils {

    private AnimUtils() {

    }

    public static void startPulseAnim(View view) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    public static void startSlideUpAnim(View view) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        ViewParent parent = view.getParent();
        int halfHeight = ((View) parent).getHeight() / 2;
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", 0f, -halfHeight);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha).with(animatorY);
        animatorSet.setDuration(500).start();
    }
}
