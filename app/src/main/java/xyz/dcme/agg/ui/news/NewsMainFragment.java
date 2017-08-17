package xyz.dcme.agg.ui.news;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.base.BaseFragment;
import xyz.dcme.library.base.BaseFragmentAdapter;

public class NewsMainFragment extends BaseFragment {

    public static final String LOG_TAG = "NewsMainFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentAdapter mAdapter;

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.news_tab);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.news_pager);
        mAdapter = new BaseFragmentAdapter(getChildFragmentManager(), makeFrags(), makeTitles());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<String> makeTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(Constants.HOME);
        Collections.addAll(titles, Constants.TABS);
        return titles;
    }

    private List<Fragment> makeFrags() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        List<String> titles = makeTitles();
        for (String t : titles) {
            NewsFragment fragment = NewsFragment.newInstance(t);
            fragments.add(fragment);
        }
        return fragments;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_main;
    }
}
