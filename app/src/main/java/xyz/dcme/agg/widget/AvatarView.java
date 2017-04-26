package xyz.dcme.agg.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.ApiLevelHelper;

public class AvatarView extends AppCompatImageView {

    private static final int NOT_FOUND = 0;

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarView,
                defStyleAttr, 0);
        try {
            final int avatarDrawableId = a.getResourceId(R.styleable.AvatarView_avatar, NOT_FOUND);
            if (avatarDrawableId != NOT_FOUND) {
                setAvatar(avatarDrawableId);
            }
        } finally {
            a.recycle();
        }
    }

    @SuppressLint("NewApi")
    private void setAvatar(@DrawableRes int avatarDrawableId) {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            setClipToOutline(true);
            setImageResource(avatarDrawableId);
        } else {
            setAvatarPreLollipop(avatarDrawableId);
        }
    }

    private void setAvatarPreLollipop(int avatarDrawableId) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),
                avatarDrawableId, getContext().getTheme());
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        @SuppressWarnings("ConstantConditions")
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmapDrawable.getBitmap());
        roundedBitmapDrawable.setCircular(true);
        setImageDrawable(roundedBitmapDrawable);
    }


    @Override
    @SuppressWarnings("NewApi")
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)
                && w > 0 && h > 0) {
            setOutlineProvider(new RoundOutlineProvider(Math.min(w, h)));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
