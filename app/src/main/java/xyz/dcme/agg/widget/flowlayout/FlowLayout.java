package xyz.dcme.agg.widget.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;

public class FlowLayout extends ViewGroup {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public static final int CENTER = 0;
    private int mGravity;
    private List<View> mLineViews = new ArrayList<>();
    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();
    private List<Integer> mLineWidth = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mGravity = typedArray.getInt(R.styleable.TagFlowLayout_gravity, LEFT);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                if (i == childCount - 1) {
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
                continue;
            }
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // children width sum is great then line width
            if (lineWidth + childWidth + getPaddingLeft() + getPaddingRight() > sizeWidth) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLineViews.clear();
        mAllViews.clear();
        mLineWidth.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();

            if (measuredWidth + lineWidth + lp.leftMargin + lp.rightMargin
                    + getPaddingLeft() + getPaddingRight() > width) {
                mLineHeight.add(lineHeight);
                mAllViews.add(mLineViews);
                mLineWidth.add(lineWidth);

                lineWidth = 0;
                lineHeight = measuredHeight + lp.topMargin + lp.bottomMargin;
                mLineViews.clear();
            }
            lineWidth += measuredWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, measuredHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }
        mLineWidth.add(lineWidth);
        mLineHeight.add(lineHeight);
        mAllViews.add(mLineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; ++i) {
            mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            // set gravity
            int curLineWidth = mLineWidth.get(i);
            switch (mGravity) {
                case LEFT: {
                    left = getPaddingLeft();
                    break;
                }
                case RIGHT: {
                    left = width - curLineWidth + getPaddingLeft();
                    break;
                }
                case CENTER: {
                    left = (width - curLineWidth) / 2 + getPaddingLeft();
                    break;
                }
            }

            for (int j = 0; j < mLineViews.size(); ++j) {
                View child = mLineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += lp.leftMargin + child.getMeasuredWidth() +lp.rightMargin;
            }
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
