package xyz.dcme.agg.ui.node;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.common.irecyclerview.IRecyclerView;
import xyz.dcme.agg.common.irecyclerview.OnLoadMoreListener;
import xyz.dcme.agg.model.Post;
import xyz.dcme.library.base.BaseFragment;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.agg.util.AnimationUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.util.LogUtils;
import xyz.dcme.agg.widget.LoadingTip;

public class NodeListFragment extends BaseFragment
        implements NodeListContract.View, SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener {
    private static final String LOG_TAG = "NodeListFragment";
    private String mNodeName;
    private IRecyclerView mNodeList;
    private PostCommonAdapter mAdapter;
    private ArrayList<Post> mData = new ArrayList<>();
    private int mNextPage = 2;
    private NodeListContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefresh;
    private LoadingTip mLoadingTips;
    private LoginReceiver mReceiver;

    public static Fragment newInstance(Node node) {
        NodeListFragment fragment = new NodeListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.NODE_NAME, node.getName());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mNodeName = args.getString(Constants.NODE_NAME);
        }
        registerBroadcast();
    }

    @Override
    public void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    @Override
    protected void initView() {
        mNodeList = (IRecyclerView) mRootView.findViewById(R.id.node_list);
        mSwipeRefresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);
        mLoadingTips = (LoadingTip) mRootView.findViewById(R.id.loading_tips);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mNodeList.setLayoutManager(lm);
        mNodeList.setOnLoadMoreListener(this);
        mSwipeRefresh.setOnRefreshListener(this);

        mAdapter = new PostCommonAdapter(getActivity(), R.layout.item_post, mData);
        mNodeList.setAdapter(mAdapter);

        mPresenter.start(mNodeName);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new NodeListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_node_list;
    }

    @Override
    public void setPresenter(NodeListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Post> data) {
        mSwipeRefresh.setRefreshing(false);
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Post> data) {
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void startLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showIndicator(final boolean isActive) {
        AnimationUtils.showProgress(mLoadingTips, mNodeList, isActive);
    }

    @Override
    public void showLoginTips() {
        mSwipeRefresh.setVisibility(View.GONE);
        mLoadingTips.setLoadingTip(LoadingTip.LoadStatus.NEED_LOGIN);
        mLoadingTips.setOnReloadListener(new LoadingTip.OnReloadListener() {
            @Override
            public void onReload() {
                startLogin();
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(true);
        mPresenter.refresh(mNodeName);
    }

    @Override
    public void onLoadMore(View view) {
        mPresenter.load(mNodeName, mNextPage);
    }

    public void registerBroadcast() {
        if (mReceiver == null) {
            mReceiver = new LoginReceiver();
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ACTION_LOGIN_SUCCESS);
            lbm.registerReceiver(mReceiver, filter);
        }
    }

    public void unRegisterBroadcast() {
        if (mReceiver != null) {
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
            lbm.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    private class LoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            LogUtils.d(LOG_TAG, "onReceive -> action: " + action);
            if (Constants.ACTION_LOGIN_SUCCESS.equals(action)) {
                showIndicator(false);
                onRefresh();
            }
        }
    }
}
