package xyz.dcme.agg.base;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import xyz.dcme.arch.QMUIFragment;


public abstract class BaseFragment extends QMUIFragment {

    public BaseFragment() {
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }


    @Override
    protected boolean canDragBack() {
        return false;
    }
}
