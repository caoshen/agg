package xyz.dcme.agg.ui.news;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.main.MainActivity;
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
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        initToolbar(toolbar, R.string.menu_home_page, R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.news_tab);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.news_pager);
        mAdapter = new BaseFragmentAdapter(getChildFragmentManager(), makeFrags(), makeTitles());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initToolbar(Toolbar toolbar, int strId, int iconId, View.OnClickListener listener) {
        super.initToolbar(toolbar, strId, iconId, listener);
    }

    private List<String> makeTitles() {
        String[] titles = getResources().getStringArray(R.array.tab_titles);
        return Arrays.asList(titles);
    }

    private List<String> makeTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add(Constants.HOME);
        Collections.addAll(tabs, Constants.TABS);
        return tabs;
    }

    private List<Fragment> makeFrags() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        List<String> titles = makeTabs();
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
