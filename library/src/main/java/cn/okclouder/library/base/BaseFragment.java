package cn.okclouder.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    protected void getArgs() {

    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected abstract void initView();

    protected abstract void initPresenter();

    protected abstract int getLayoutId();

    protected void initToolbar(Toolbar toolbar, int strId, int iconId, final View.OnClickListener listener) {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setTitle(strId);
            }
        }
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationIcon(iconId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
