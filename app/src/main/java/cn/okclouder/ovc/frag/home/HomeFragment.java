package cn.okclouder.ovc.frag.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.frag.explore.ExploreTabFragment;
import cn.okclouder.ovc.frag.myself.MySelfFragment;
import cn.okclouder.ovc.frag.recommend.RecommendTabFragment;
import cn.okclouder.ovc.frag.whatshot.WhatsHotFragment;

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ViewPager mViewPager;
    private QMUITabSegment mTabs;
    private PagerAdapter mAdapter;
    private HashMap<Pager, Fragment> mPages;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);
        initTabs(view);
        initPagers(view);
        return view;
    }

    private void initPagers(View view) {
        mViewPager = view.findViewById(R.id.home_pager);
        mPages = new HashMap<>();
        RecommendTabFragment recommendTabFragment = new RecommendTabFragment();
        WhatsHotFragment whatsHotFragment = new WhatsHotFragment();
        ExploreTabFragment exploreTabFragment = new ExploreTabFragment();
        MySelfFragment mySelfFragment = new MySelfFragment();

        mPages.put(Pager.HOME, recommendTabFragment);
        mPages.put(Pager.HOT, whatsHotFragment);
        mPages.put(Pager.DISCOVER, exploreTabFragment);
        mPages.put(Pager.ME, mySelfFragment);

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Pager pagerFromPosition = Pager.getPagerFromPosition(position);
                return mPages.get(pagerFromPosition);
            }

            @Override
            public int getCount() {
                return mPages.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager, false);
    }

    private void initTabs(View view) {
        mTabs = view.findViewById(R.id.home_tabs);
        int defaultColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_gray_6);
        int selectedColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_blue);
        mTabs.setDefaultNormalColor(defaultColor);
        mTabs.setDefaultSelectedColor(selectedColor);

        QMUITabSegment.Tab homeTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_home_normal),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_home),
                "", false);

        QMUITabSegment.Tab discoverTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_whatshot_normal),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_whatshot),
                "", false, true);

        QMUITabSegment.Tab hotTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_widgets_normal),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_widgets),
                "", false, true);

        QMUITabSegment.Tab meTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_person_normal),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_person),
                "", false, true);

        mTabs.addTab(homeTab)
                .addTab(discoverTab)
                .addTab(hotTab)
                .addTab(meTab);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    enum Pager {
        HOME, HOT, DISCOVER, ME;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return HOME;
                case 1:
                    return HOT;
                case 2:
                    return DISCOVER;
                case 3:
                    return ME;
                default:
                    return HOME;
            }
        }
    }

}
