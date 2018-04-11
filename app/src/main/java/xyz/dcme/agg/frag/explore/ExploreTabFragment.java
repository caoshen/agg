package xyz.dcme.agg.frag.explore;

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

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.agg.ui.node.Node;
import xyz.dcme.agg.ui.node.NodeMainContract;
import xyz.dcme.agg.ui.node.NodeMainPresenter;
import xyz.dcme.agg.ui.node.NodeManagerActivity;
import xyz.dcme.library.base.BaseFragmentAdapter;
import xyz.dcme.library.util.LogUtils;


public class ExploreTabFragment extends BaseFragment implements View.OnClickListener, NodeMainContract.View {
    private static final int REQUEST_CHOOSE_NODE = 1000;
    private static final String LOG_TAG = "ExploreTabFragment";
    private NodeMainContract.Presenter mPresenter;
    private BaseFragmentAdapter mAdapter;
    private QMUITabSegment mTabs;
    private ViewPager mPager;
    private Node mSelectNode;

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
                Intent intent = new Intent(getActivity(), NodeManagerActivity.class);
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
        if (nodes == null || nodes.isEmpty()) {
            LogUtils.d(LOG_TAG, "showNodes -> nodes is empty");
            return;
        }

        List<String> nodeNames = new ArrayList<>();
        List<Fragment> nodeFragments = new ArrayList<>();
        for (Node n : nodes) {
            nodeNames.add(n.getTitle());
            nodeFragments.add(makeFrags(n));
        }

        if (mAdapter == null) {
            mAdapter = new BaseFragmentAdapter(getChildFragmentManager(),
                    nodeFragments, nodeNames);
        } else {
            mAdapter.setFragments(getChildFragmentManager(), nodeFragments, nodeNames);
        }
        mPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mPager, true);

        mSelectNode = nodes.get(0);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position < 0 || position >= nodes.size()) {
                    return;
                }
                mSelectNode = nodes.get(position);
            }
        });
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
