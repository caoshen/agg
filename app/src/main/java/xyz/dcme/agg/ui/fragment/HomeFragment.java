package xyz.dcme.agg.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.qmuiteam.qmui.widget.QMUITabSegment;

import xyz.dcme.agg.R;
import xyz.dcme.library.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    private ViewPager mViewPager;
    private QMUITabSegment mTabs;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    protected void initView() {
        mViewPager = mRootView.findViewById(R.id.home_pager);
        mTabs = mRootView.findViewById(R.id.home_tabs);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
