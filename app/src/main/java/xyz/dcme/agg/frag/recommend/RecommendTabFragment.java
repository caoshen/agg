package xyz.dcme.agg.frag.recommend;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.base.BaseFragmentAdapter;


public class RecommendTabFragment extends BaseFragment {
    private BaseFragmentAdapter mAdapter;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recomment_tab, null);
        initTabAndPager(rootView);
        return rootView;
    }

    private void initTabAndPager(View rootView) {
        QMUITabSegment tabs = rootView.findViewById(R.id.tabSegment);
        ViewPager viewPager = rootView.findViewById(R.id.contentViewPager);
        List<Fragment> frags = makeFrags();
        List<String> titles = makeTitles();
        if (frags.size() != titles.size()) {
            return;
        }
        mAdapter = new BaseFragmentAdapter(getChildFragmentManager(), frags, titles);
        viewPager.setAdapter(mAdapter);

        tabs.setHasIndicator(true);
        tabs.setMode(QMUITabSegment.MODE_SCROLLABLE);
        tabs.setTabTextSize(QMUIDisplayHelper.dp2px(getContext(), 14));
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        tabs.setItemSpaceInScrollMode(space);
        tabs.setPadding(space, 0, space, 0);
        tabs.setupWithViewPager(viewPager, true);
    }

    private List<String> makeTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add(Constants.HOME);
        Collections.addAll(tabs, Constants.TABS_NOT_LOGIN);
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
        String[] titles = getResources().getStringArray(R.array.tab_titles_not_login);
        return Arrays.asList(titles);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
