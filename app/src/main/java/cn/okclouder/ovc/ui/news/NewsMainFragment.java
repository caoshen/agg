package cn.okclouder.ovc.ui.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.ui.main.MainActivity;
import cn.okclouder.ovc.ui.publish.PublishActivity;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.library.base.BaseFragment;
import cn.okclouder.library.base.BaseFragmentAdapter;

public class NewsMainFragment extends BaseFragment {

    public static final String LOG_TAG = "NewsMainFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_node_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_publish) {
            PublishActivity.startPublish(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}