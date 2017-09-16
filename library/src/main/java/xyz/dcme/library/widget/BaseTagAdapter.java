package xyz.dcme.library.widget;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseTagAdapter<V extends BaseTagView<T>, T> {
    private final Context mContext;
    private final List<T> mData;
    protected int mDefaultDrawable;
    protected int mSelectedDrawable;
    protected int mDefaultTextColor;
    protected int mSelectedTextColor;
    private int mMode;
    private int mMaxSelection;
    private boolean mShowHighlight = true;
    private List<T> mSelection;
    private TagFlowLayout mTagFlowLayout;
    private Map<V, T> mViewMap = new ArrayMap<>();
    private IItemSelectedListener mSelectedListener;

    public BaseTagAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    public BaseTagAdapter(Context context, List<T> data, List<T> selection) {
        mContext = context;
        mData = data;
        mSelection = selection;
    }

    public void setSelectedListener(IItemSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getData() {
        return mData;
    }

    public List<T> getSelection() {
        return mSelection;
    }

    public void bindView(TagFlowLayout tagFlowLayout) {
        mTagFlowLayout = tagFlowLayout;
        mDefaultDrawable = tagFlowLayout.getDefaultDrawable();
        mSelectedDrawable = tagFlowLayout.getSelectedDrawable();
        mDefaultTextColor = tagFlowLayout.getDefaultTextColor();
        mSelectedTextColor = tagFlowLayout.getSelectedTextColor();
        mMaxSelection = tagFlowLayout.getMaxSelection();
        mMode = tagFlowLayout.getMode();
        mShowHighlight = tagFlowLayout.isShowHighlight();
    }

    public void addTags() {
        if (mData == null || mData.isEmpty()) {
            return;
        }
        mTagFlowLayout.removeAllViews();
        for (T t : mData) {
            if (t == null) {
                continue;
            }
            final BaseTagView<T> tagView = addTag(t);
            initSelectView(tagView);
            tagView.setListener(new BaseTagView.ITagSelectedListener<T>() {
                @Override
                public void onItemSelected(T item) {
                    if (mMode == TagFlowLayout.SINGLE_SELECT) {
                        enterSingleMode(item);
                    } else {
                        List<T> selectItems = getSelectItems();
                        if (mMaxSelection > 0 && mMaxSelection < selectItems.size()
                                && !tagView.isChecked()) {
                            String tips = "最多选择" + mMaxSelection + "个";
                            Toast.makeText(getContext(), tips, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mShowHighlight) {
                            tagView.toggle();
                        }
                    }
                    if (null != mSelectedListener) {
                        mSelectedListener.onItemSelect(getSelectItems());
                    }
                }
            });
            mViewMap.put((V) tagView, t);
            mTagFlowLayout.addView(tagView);
        }
    }

    private List<T> getSelectItems() {
        List<T> selected = new ArrayList<>();
        for (V v : mViewMap.keySet()) {
            if (v.isChecked()) {
                T t = mViewMap.get(v);
                selected.add(t);
            }
        }
        return selected;
    }

    private void enterSingleMode(T item) {
        if (!mShowHighlight) {
            return;
        }
        for (V tagView : mViewMap.keySet()) {
            tagView.setChecked(isItemSame(tagView, item));
        }
    }

    public void notifyDataSetChanged() {
        addTags();
    }

    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    private void initSelectView(BaseTagView<T> tagView) {
        if (!mShowHighlight) {
            return;
        }
        if (null == mSelection || mSelection.isEmpty()) {
            return;
        }
        for (T item : mSelection) {
            if (isItemNull(item)) {
                continue;
            }
            if (isItemSame((V) tagView, item)) {
                tagView.setChecked(true);
                break;
            }
        }
    }

    protected abstract boolean isItemSame(V tagView, T item);

    protected abstract boolean isItemNull(T item);

    protected abstract BaseTagView<T> addTag(T t);
}
