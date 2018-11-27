package cn.okclouder.ovc.widget;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * 圆形外观，对 Lollipop 及以上版本有效
 * Created by CaoShen on 2016/7/26.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RoundOutlineProvider extends ViewOutlineProvider {
    private final int mSize;

    public RoundOutlineProvider(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size need to be > 0. Actually was "
                + size);
        }
        mSize = size;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setOval(0, 0, mSize, mSize);
    }
}
