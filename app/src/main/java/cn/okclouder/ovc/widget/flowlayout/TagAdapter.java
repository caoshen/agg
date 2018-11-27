package cn.okclouder.ovc.widget.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter for TagFlowLayout
 *
 * @param <T>
 */
public abstract class TagAdapter<T> {

    private List<T> mTagData;
    private OnDataChangeListener mOnDataChangeListener;
    private HashSet<Integer> mCheckedPosSet = new HashSet<>();

    public TagAdapter(List<T> tags) {
        mTagData = tags;
    }

    public TagAdapter(T[] tags) {
        mTagData = new ArrayList<T>(Arrays.asList(tags));
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    public void setSelectedPos(Set<Integer> set) {
        mCheckedPosSet.clear();
        if (set != null && !set.isEmpty()) {
            mCheckedPosSet.addAll(set);
        }
        notifyDataChanged();
    }

    public void setSelectedPos(int... pos) {
        Set<Integer> posSet = new HashSet<>();
        for (int p : pos) {
            posSet.add(p);
        }
        setSelectedPos(posSet);
    }

    public HashSet<Integer> getPreCheckedPos() {
        return mCheckedPosSet;
    }

    public int getCount() {
        return mTagData == null ? 0 : mTagData.size();
    }

    public T getItem(int position) {
        return mTagData.get(position);
    }

    private void notifyDataChanged() {
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChange();
        }
    }

    public abstract View getView(FlowLayout parent, int position, T t);

    public boolean setSelected(int position, T t) {
        return false;
    }
}
