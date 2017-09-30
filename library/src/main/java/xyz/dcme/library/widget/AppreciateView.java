package xyz.dcme.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import xyz.dcme.library.R;
import xyz.dcme.library.anim.AnimUtils;


public class AppreciateView extends FrameLayout implements Checkable {
    private static final int[] CHECK_STATE = {android.R.attr.state_checked};
    private boolean mIsChecked;
    private CheckableImageView mCheckableImageView;
    private TextView mTextView;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public AppreciateView(@NonNull Context context) {
        this(context, null);
    }

    public AppreciateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppreciateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        setClickable(true);
        mCheckableImageView = new CheckableImageView(getContext());
        mCheckableImageView.setBackgroundResource(R.drawable.bg_appreciate);
        mCheckableImageView.setDuplicateParentStateEnabled(true);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        addView(mCheckableImageView, lp);

        mTextView = new TextView(getContext());
        mTextView.setText("+1");
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mTextView.setTextColor(Color.parseColor("#2196F3"));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(View.GONE);
        addView(mTextView, lp);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
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
        if (mIsChecked) {
            AnimUtils.startPulseAnim(mCheckableImageView);
            mTextView.setVisibility(View.VISIBLE);
            AnimUtils.startSlideUpAnim(mTextView);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(View view, boolean isChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }
}
