package xyz.dcme.agg.ui.node;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.ui.BaseFragmentAdapter;
import xyz.dcme.agg.util.LogUtils;

public class NodeMainFragment extends BaseFragment
        implements NodeMainContract.View, View.OnClickListener {
    public static final String LOG_TAG = "NodeMainFragment";

    private TabLayout mTab;
    private ViewPager mPager;
    private TextView mAdd;
    private NodeMainContract.Presenter mPresenter;
    private BaseFragmentAdapter mAdapter;

    public static NodeMainFragment newInstance() {
        return new NodeMainFragment();
    }

    @Override
    protected void initView() {
        mTab = (TabLayout) mRootView.findViewById(R.id.tab);
        mPager = (ViewPager) mRootView.findViewById(R.id.node_pager);
        mAdd = (TextView) mRootView.findViewById(R.id.add_node);
        mAdd.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        mPresenter.load();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new NodeMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_node;
    }

    @Override
    public void setPresenter(NodeMainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNodes(List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            LogUtils.LOGD(LOG_TAG, "showNodes -> nodes is empty");
            return;
        }

        List<String> nodeNames = new ArrayList<>();
        List<Fragment> nodeFragments = new ArrayList<>();
        for (Node n : nodes) {
            nodeNames.add(n.getNodeName());
            nodeFragments.add(createFragment(n));
        }

        if (mAdapter == null) {
            mAdapter = new BaseFragmentAdapter(getChildFragmentManager(),
                    nodeFragments, nodeNames);
        } else {
            mAdapter.setFragments(getChildFragmentManager(), nodeFragments, nodeNames);
        }
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }

    @Override
    public void showIndicator(boolean isActive) {

    }

    @Override
    public void showErrorTip() {

    }

    private Fragment createFragment(Node node) {
        return NodeListFragment.newInstance(node);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_node: {
                Intent intent = new Intent(getActivity(), NodeChooseActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
