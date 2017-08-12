package xyz.dcme.agg.widget.flowlayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import xyz.dcme.library.util.LogUtils;

public class TagFlowLayout extends FlowLayout implements OnDataChangeListener {
    private static final String LOG_TAG = "TagFlowLayout";
    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT = "key_default";
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    private TagAdapter mAdapter;
    private boolean mAutoSelectEffect = true;
    private int mSelectedMax = -1;
    private MotionEvent mMotionEvent;
    private Set<Integer> mSelectedView = new HashSet<>();

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            TagView child = (TagView) getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            if (child.getTagView().getVisibility() == GONE) {
                child.setVisibility(GONE);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
        if (mOnSelectListener != null) {
            setClickable(true);
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
        if (mOnTagClickListener != null) {
            setClickable(true);
        }
    }

    @Override
    public void onDataChange() {
        mSelectedView.clear();
        changeAdapter();
    }

    @SuppressWarnings("ResourceType")
    private void changeAdapter() {
        removeAllViews();
        TagAdapter adapter = mAdapter;
        TagView tagViewContainer = null;
        HashSet preCheckedList = mAdapter.getPreCheckedPos();
        for (int i = 0; i < adapter.getCount(); i++) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));
            tagViewContainer = new TagView(getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }
            addView(tagViewContainer);

            if (preCheckedList.contains(i)) {
                tagViewContainer.setChecked(true);
            }
            if (mAdapter.setSelected(i, adapter.getItem(i))) {
                mSelectedView.add(i);
                tagViewContainer.setChecked(true);
            }
        }
        mSelectedView.addAll(preCheckedList);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null) {
            return super.performClick();
        }

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        TagView child = findChild(x, y);
        int pos = findPosByView(child);
        if (child != null) {
            doSelect(child, pos);
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(child.getTagView(), pos, this);
            }
        }
        return true;
    }

    public void setMaxSelectCount(int count) {
        if (mSelectedView.size() > count) {
            LogUtils.w(LOG_TAG, "max select count > " + count);
            mSelectedView.clear();
        }
        mSelectedMax = count;
    }

    public Set<Integer> getSelectedSet() {
        return new HashSet<Integer>(mSelectedView);
    }

    private void doSelect(TagView child, int position) {
        if (mAutoSelectEffect) {
            if (!child.isChecked()) {
                if (mSelectedMax == 1 && mSelectedView.size() == 1) {
                    Iterator<Integer> iterator = mSelectedView.iterator();
                    Integer preIndex = iterator.next();
                    TagView pre = (TagView) getChildAt(preIndex);
                    pre.setChecked(false);
                    child.setChecked(true);
                    mSelectedView.remove(preIndex);
                    mSelectedView.add(position);
                } else {
                    if (mSelectedMax > 0 && mSelectedView.size() >= mSelectedMax)
                        return;
                    child.setChecked(true);
                    mSelectedView.add(position);
                }
            } else {
                child.setChecked(false);
                mSelectedView.remove(position);
            }
            if (mOnSelectListener != null) {
                mOnSelectListener.onSelect(new HashSet<Integer>(mSelectedView));
            }
        }
    }

    public TagAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(TagAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnDataChangeListener(this);
        mSelectedView.clear();
        changeAdapter();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());

        String selectPos = "";
        if (mSelectedView.size() > 0) {
            for (int key : mSelectedView) {
                selectPos += key + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            String mSelectPos = bundle.getString(KEY_CHOOSE_POS);
            if (!TextUtils.isEmpty(mSelectPos)) {
                String[] split = mSelectPos.split("\\|");
                for (String pos : split) {
                    int index = Integer.parseInt(pos);
                    mSelectedView.add(index);

                    TagView tagView = (TagView) getChildAt(index);
                    if (tagView != null)
                        tagView.setChecked(true);
                }

            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private int findPosByView(TagView child) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private TagView findChild(int x, int y) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            TagView v = (TagView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y)) {
                return v;
            }
        }
        return null;
    }

    private int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnSelectListener {
        void onSelect(Set<Integer> selectPosSet);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }
}
