package cn.okclouder.library.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;


public class BaseTagView<T> extends FrameLayout implements Checkable, View.OnClickListener {

    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    private boolean mChecked;
    private T mItem;
    private TextView mTextView;
    private ITagSelectedListener<T> mListener;
    private int mDefaultDrawable;
    private int mSelectedDrawable;
    private int mDefaultTextColor;
    private int mSelectedTextColor;

    public BaseTagView(@NonNull Context context) {
        this(context, null);
    }

    public BaseTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTagView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(getContext());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setDuplicateParentStateEnabled(true);
        addView(mTextView);
        setOnClickListener(this);
    }

    public void setListener(ITagSelectedListener<T> listener) {
        mListener = listener;
    }

    public int getDefaultDrawable() {
        return mDefaultDrawable;
    }

    public void setDefaultDrawable(int defaultDrawable) {
        mDefaultDrawable = defaultDrawable;
        setBackgroundResource(defaultDrawable);
    }

    public int getSelectedDrawable() {
        return mSelectedDrawable;
    }

    public void setSelectedDrawable(int selectedDrawable) {
        mSelectedDrawable = selectedDrawable;
    }

    public int getDefaultTextColor() {
        return mDefaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        mDefaultTextColor = defaultTextColor;
        mTextView.setTextColor(getResources().getColor(defaultTextColor));
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace + 1);
        if (mChecked) {
            mergeDrawableStates(state, CHECK_STATE);
        }
        return state;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemSelected(mItem);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            setBackgroundResource(mChecked ? mSelectedDrawable : mDefaultDrawable);
            mTextView.setTextColor(getResources().getColor(mChecked ? mSelectedTextColor : mDefaultTextColor));
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public T getItem() {
        return mItem;
    }

    public void setItem(T item) {
        mItem = item;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }

    public interface ITagSelectedListener<T> {
        void onItemSelected(T item);
    }
}
