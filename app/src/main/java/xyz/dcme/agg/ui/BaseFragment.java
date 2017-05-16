package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            int layoutId = getLayoutId();
            mRootView = inflater.inflate(layoutId, container, false);
        }
        initPresenter();
        initView();
        return mRootView;
    }

    protected abstract void initView();

    protected abstract void initPresenter();

    protected abstract int getLayoutId();
}
