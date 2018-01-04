package xyz.dcme.agg.ui.fragment;

import android.content.AsyncQueryHandler;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.qmuiteam.qmui.util.QMUIResHelper;
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
        initTabs();
        initPagers();
    }

    private void initPagers() {
        mViewPager = mRootView.findViewById(R.id.home_pager);
    }

    private void initTabs() {
        mTabs = mRootView.findViewById(R.id.home_tabs);
        int defaultColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_gray_6);
        int selectedColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_blue);
        mTabs.setDefaultNormalColor(defaultColor);
        mTabs.setDefaultSelectedColor(selectedColor);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
