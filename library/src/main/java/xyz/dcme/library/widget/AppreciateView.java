package xyz.dcme.library.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;


public class AppreciateView extends FrameLayout implements Checkable {
    private boolean mIsChecked;
    private CheckableImageView mCheckableImageView;
    private TextView mTextView;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public AppreciateView(@NonNull Context context) {
        super(context);
    }

    public AppreciateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppreciateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mIsChecked != checked) {
            mIsChecked = checked;
            refreshDrawableState();
            if (null != mOnCheckedChangeListener) {
                mOnCheckedChangeListener.onCheckedChange(this, mIsChecked);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(View view, boolean isChecked);
    }
}
