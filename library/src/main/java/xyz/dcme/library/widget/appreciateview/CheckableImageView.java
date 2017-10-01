package xyz.dcme.library.widget.appreciateview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckableImageView extends android.support.v7.widget.AppCompatImageView implements Checkable {
    private static final int[] CHECKED_STATE = {android.R.attr.state_checked};
    private boolean mIsChecked = false;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked != mIsChecked) {
            mIsChecked = checked;
            refreshDrawableState();
            if (null != mOnCheckedChangeListener) {
                mOnCheckedChangeListener.onCheckedChange(this, checked);
            }
        }
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (mIsChecked) {
            mergeDrawableStates(states, CHECKED_STATE);
        }
        return states;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(CheckableImageView view, boolean isChecked);
    }
}
