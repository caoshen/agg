package xyz.dcme.agg.ui.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.common.irecyclerview.IRecyclerView;
import xyz.dcme.agg.common.irecyclerview.OnLoadMoreListener;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.AnimationUtils;

public class TopicFragment extends BaseFragment
        implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener {

    private static final String KEY_USER_NAME = "key_username";
    private IRecyclerView mTopicView;
    private ProgressBar mProgressBar;

    private String mUserName;
    private TopicContract.Presenter mPresenter;
    private List<Post> mData;
    private Toolbar mToolbar;
    private PostCommonAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private int mNextPage = 2;

    public static Fragment newInstance(String userName) {
        Fragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(KEY_USER_NAME);
        }
    }

    @Override
    protected void initView() {
        mTopicView = (IRecyclerView) mRootView.findViewById(R.id.topic_list);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mSwipeRefresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.topic_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        initToolbar();
        initRecycle();

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start(mUserName);
    }

    @Override
    public void initPresenter() {
        mPresenter = new TopicPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    private void initRecycle() {
        mData = new ArrayList<>();
        mTopicView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PostCommonAdapter(getActivity(), R.layout.item_post, mData);
        mTopicView.setAdapter(mAdapter);
        mTopicView.setOnLoadMoreListener(this);
    }

    private void initToolbar() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
            ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                int titleResId = AccountUtils.isCurrentAccount(activity, mUserName)
                        ? R.string.topic : R.string.his_topic;
                ab.setTitle(titleResId);
            }
        }
    }

    @Override
    public void setPresenter(TopicContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showIndicator(final boolean active) {
        AnimationUtils.showProgress(mProgressBar, mTopicView, active);
    }

    @Override
    public void showRefresh(List<Post> topics) {
        mSwipeRefresh.setRefreshing(false);
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(topics);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Post> data) {
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(true);
        mPresenter.refresh(mUserName);
    }

    @Override
    public void onLoadMore(View view) {
        mPresenter.load(mUserName, mNextPage);
    }
}
