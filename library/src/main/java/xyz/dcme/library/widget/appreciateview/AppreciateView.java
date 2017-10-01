package xyz.dcme.library.widget.appreciateview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import xyz.dcme.library.R;
import xyz.dcme.library.anim.AnimUtils;
import xyz.dcme.library.util.DisplayUtils;


public class AppreciateView extends FrameLayout implements Checkable {
    private static final int[] CHECK_STATE = {android.R.attr.state_checked};
    public static final String DEFAULT_TEXT = "+1";
    public static final int DEFAULT_TEXT_SIZE = 4;
    public static final int DEFAULT_IMAGE_SIZE = 20;
    private float mTextSize;
    private int mTextColor;
    private float mImageSize;
    private String mText;
    private boolean mIsChecked;
    private CheckableImageView mCheckableImageView;
    private TextView mTextView;
    private int mImageBackground;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppreciateView);
        mImageBackground = typedArray.getResourceId(R.styleable.AppreciateView_image_background, R.drawable.bg_appreciate);
        mImageSize = typedArray.getDimension(R.styleable.AppreciateView_image_size, 0);
        mText = typedArray.getString(R.styleable.AppreciateView_text);
        mTextColor = typedArray.getColor(R.styleable.AppreciateView_text_color, getResources().getColor(R.color.checked_color));
        mTextSize = typedArray.getDimension(R.styleable.AppreciateView_text_size, 0);
        typedArray.recycle();
        initViews();
    }

    private void initViews() {
        setClickable(true);
        mCheckableImageView = new CheckableImageView(getContext());
        mCheckableImageView.setBackgroundResource(mImageBackground);
        mCheckableImageView.setDuplicateParentStateEnabled(true);
        if (mImageSize == 0) {
            mImageSize = DisplayUtils.dp2px(getContext(), DEFAULT_IMAGE_SIZE);
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) mImageSize, (int) mImageSize);
        lp.gravity = Gravity.CENTER;
        addView(mCheckableImageView, lp);

        mTextView = new TextView(getContext());
        if (TextUtils.isEmpty(mText)) {
            mText = DEFAULT_TEXT;
        }
        if (mTextSize == 0) {
            mTextSize = DisplayUtils.sp2px(getContext(), DEFAULT_TEXT_SIZE);
        }
        mTextView.setText(mText);
        mTextView.setTextSize(mTextSize);
        mTextView.setTextColor(mTextColor);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(View.GONE);
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        tlp.gravity = Gravity.CENTER;
        addView(mTextView, tlp);
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
