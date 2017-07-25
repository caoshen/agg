package xyz.dcme.agg.widget.flowlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;


public class TagView extends FrameLayout implements Checkable {

    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    private boolean mIsChecked = false;

    public TagView(@NonNull Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (mIsChecked) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mIsChecked != checked) {
            mIsChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);
    }
}
