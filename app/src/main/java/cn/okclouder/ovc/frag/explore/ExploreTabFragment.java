package cn.okclouder.ovc.frag.explore;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.base.BaseFragmentStateAdapter;
import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.frag.home.HomeControllerFragment;
import cn.okclouder.ovc.frag.node.NodeAllCategoryActivity;
import cn.okclouder.ovc.ui.node.Node;
import cn.okclouder.ovc.ui.node.NodeMainContract;
import cn.okclouder.ovc.ui.node.NodeMainPresenter;


public class ExploreTabFragment extends HomeControllerFragment implements View.OnClickListener, NodeMainContract.View {
    private static final int REQUEST_CHOOSE_NODE = 1000;
    private static final String LOG_TAG = "ExploreTabFragment";
    private NodeMainContract.Presenter mPresenter;
    private BaseFragmentStateAdapter mAdapter;
    private QMUITabSegment mTabs;
    private ViewPager mPager;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_explore_tab, null);
        initPresenter();
        initTabAndPager(rootView);
        return rootView;
    }

    private void initPresenter() {
        mPresenter = new NodeMainPresenter(this);
    }

    private void initTabAndPager(View rootView) {
        mTabs = (QMUITabSegment) rootView.findViewById(R.id.tabSegment);
        mPager = (ViewPager) rootView.findViewById(R.id.contentViewPager);
        TextView moreText = (TextView) rootView.findViewById(R.id.more);
        moreText.setOnClickListener(this);

        mTabs.setHasIndicator(true);
        mTabs.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabs.setTabTextSize(QMUIDisplayHelper.dp2px(getContext(), 14));
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabs.setItemSpaceInScrollMode(space);
        mTabs.setPadding(space, 0, space, 0);

        mPresenter.load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more: {
                Intent intent = new Intent(getActivity(), NodeAllCategoryActivity.class);
                startActivityForResult(intent, REQUEST_CHOOSE_NODE);
                break;
            }
        }
    }

    @Override
    public void setPresenter(NodeMainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNodes(final List<Node> nodes) {
        if (nodes == null) {
            LogUtils.d(LOG_TAG, "showNodes -> nodes is null.");
            return;
        }

        List<String> nodeNames = new ArrayList<>();
        List<Fragment> nodeFragments = new ArrayList<>();
        for (Node n : nodes) {
            nodeNames.add(n.getTitle());
            nodeFragments.add(makeFrags(n));
        }

        if (mAdapter == null) {
            mAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(),
                    nodeFragments, nodeNames);
            mPager.setAdapter(mAdapter);
            mTabs.setupWithViewPager(mPager, true);
        } else {
            mAdapter.setFragments(getChildFragmentManager(), nodeFragments, nodeNames);
        }
    }

    private Fragment makeFrags(Node node) {
        return ExploreTopicFragment.newInstance(node);
    }

    @Override
    public void showIndicator(boolean isActive) {

    }

    @Override
    public void showErrorTip() {

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_NODE) {
            mPresenter.load();
        }
    }
}
