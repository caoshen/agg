package xyz.dcme.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;

import xyz.dcme.library.R;

public class TagFlowLayout extends FlexboxLayout {

    public static final int MODE_MULTI_SELECT = 0;
    public static final int MODE_SINGLE_SELECT = 1;
    private int mMode = MODE_MULTI_SELECT;
    private int mMaxSelection;
    private boolean mShowHighlight = true;
    private int mDefaultDrawable;
    private int mSelectedDrawable;
    private int mDefaultTextColor;
    private int mSelectedTextColor;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mShowHighlight = ta.getBoolean(R.styleable.TagFlowLayout_showHighlight, true);
        mDefaultDrawable = ta.getResourceId(R.styleable.TagFlowLayout_defaultDrawable, 0);
        mSelectedDrawable = ta.getResourceId(R.styleable.TagFlowLayout_selectedDrawable, 0);
        mDefaultTextColor = ta.getResourceId(R.styleable.TagFlowLayout_defaultTextColor, 0);
        mMode = ta.getResourceId(R.styleable.TagFlowLayout_mode, MODE_MULTI_SELECT);
        mMaxSelection = ta.getInt(R.styleable.TagFlowLayout_maxSelectionCount, 0);
        ta.recycle();
    }

    public int getMode() {
        return mMode;
    }

    public int getMaxSelection() {
        return mMaxSelection;
    }

    public boolean isShowHighlight() {
        return mShowHighlight;
    }

    public void setShowHighlight(boolean showHighlight) {
        mShowHighlight = showHighlight;
    }

    public int getDefaultDrawable() {
        return mDefaultDrawable;
    }

    public void setDefaultDrawable(int defaultDrawable) {
        mDefaultDrawable = defaultDrawable;
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
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setAdapter(BaseTagAdapter adapter) {
        if (null == adapter) {
            removeAllViews();
            return;
        }
        adapter.bindView(this);
        adapter.addTags();
    }
}
