package xyz.dcme.agg.ui.node;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.main.MainActivity;
import xyz.dcme.agg.ui.publish.PublishActivity;
import xyz.dcme.library.base.BaseFragment;
import xyz.dcme.library.base.BaseFragmentAdapter;
import xyz.dcme.library.util.LogUtils;

public class NodeMainFragment extends BaseFragment
        implements NodeMainContract.View, View.OnClickListener {
    public static final String LOG_TAG = "NodeMainFragment";
    private static final int REQUEST_CHOOSE_NODE = 1000;

    private TabLayout mTab;
    private ViewPager mPager;
    private TextView mAdd;
    private NodeMainContract.Presenter mPresenter;
    private BaseFragmentAdapter mAdapter;

    public static NodeMainFragment newInstance() {
        return new NodeMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initView() {
        mTab = (TabLayout) mRootView.findViewById(R.id.tab);
        mPager = (ViewPager) mRootView.findViewById(R.id.node_pager);
        mAdd = (TextView) mRootView.findViewById(R.id.add_node);
        mAdd.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        initToolbar(toolbar, R.string.explore, R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });

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
            LogUtils.d(LOG_TAG, "showNodes -> nodes is empty");
            return;
        }

        List<String> nodeNames = new ArrayList<>();
        List<Fragment> nodeFragments = new ArrayList<>();
        for (Node n : nodes) {
            nodeNames.add(n.getTitle());
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

    @Override
    public Context getViewContext() {
        return getContext();
    }

    private Fragment createFragment(Node node) {
        return NodeListFragment.newInstance(node);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_node: {
                Intent intent = new Intent(getActivity(), NodeChooseActivity.class);
                startActivityForResult(intent, REQUEST_CHOOSE_NODE);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_NODE) {
            mPresenter.load();
        }
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
