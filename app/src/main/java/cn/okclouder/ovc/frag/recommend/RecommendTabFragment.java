package cn.okclouder.ovc.frag.recommend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.AccountManager;
import cn.okclouder.account.ErrorStatus;
import cn.okclouder.account.LoginConstants;
import cn.okclouder.account.LoginHandler;
import cn.okclouder.account.OnCheckLoginClickListener;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.frag.home.HomeControllerFragment;
import cn.okclouder.ovc.frag.write.WriteActivity;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.library.base.BaseFragmentAdapter;


public class RecommendTabFragment extends HomeControllerFragment {
    private BaseFragmentAdapter mAdapter;
    private BroadcastReceiver mReceiver;
    private ViewPager mViewPager;
    private QMUITabSegment mTabs;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recomment_tab, null);
        initTabAndPager(rootView);
        initCreateArticle(rootView);
        return rootView;
    }

    private void initCreateArticle(View rootView) {
        FrameLayout create = rootView.findViewById(R.id.create_article);
        create.setOnClickListener(new OnCheckLoginClickListener(getActivity(), new LoginHandler() {
            @Override
            public void onLogin(AccountInfo account) {

            }

            @Override
            public void onError(ErrorStatus status) {

            }
        }) {
            @Override
            protected void doClick(View v) {
                WriteActivity.startPublish(getActivity());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    private void registerAccountBroadcast() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (LoginConstants.ACTION_LOGIN_SUCCESS.equals(action)
                        || LoginConstants.ACTION_LOGOUT_ACCOUNT.equals(action)) {
                    updateTabsAndPagers();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoginConstants.ACTION_LOGOUT_ACCOUNT);
        filter.addAction(LoginConstants.ACTION_LOGIN_SUCCESS);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, filter);
    }

    private void initTabAndPager(View rootView) {
        mTabs = rootView.findViewById(R.id.tabSegment);
        mViewPager = rootView.findViewById(R.id.contentViewPager);
        updateTabsAndPagers();
    }

    private void updateTabsAndPagers() {
        List<Fragment> frags = makeFrags();
        List<String> titles = makeTitles();
        if (frags.size() != titles.size()) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.setFragments(getChildFragmentManager(), frags, titles);
        } else {
            mAdapter = new BaseFragmentAdapter(getChildFragmentManager(), frags, titles);
        }
        mViewPager.setAdapter(mAdapter);

        mTabs.setHasIndicator(true);
        mTabs.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabs.setTabTextSize(QMUIDisplayHelper.dp2px(getContext(), 14));
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabs.setItemSpaceInScrollMode(space);
        mTabs.setPadding(space, 0, space, 0);
        mTabs.setupWithViewPager(mViewPager, true);
    }

    private List<String> makeTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add(Constants.HOME);
        if (!AccountManager.hasLoginAccount(getActivity())) {
            Collections.addAll(tabs, Constants.TABS_NOT_LOGIN);
        } else {
            Collections.addAll(tabs, Constants.TABS);
        }
        return tabs;
    }

    private List<Fragment> makeFrags() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        List<String> titles = makeTabs();
        for (String t : titles) {
            RecommendFragment fragment = RecommendFragment.newInstance(t);
            fragments.add(fragment);
        }
        return fragments;
    }

    private List<String> makeTitles() {
        String[] titles;
        if (!AccountManager.hasLoginAccount(getActivity())) {
            titles = getResources().getStringArray(R.array.tab_titles_not_login);
        } else {
            titles = getResources().getStringArray(R.array.tab_titles);
        }
        return Arrays.asList(titles);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
