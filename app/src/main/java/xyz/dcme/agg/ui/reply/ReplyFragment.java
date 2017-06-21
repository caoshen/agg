package xyz.dcme.agg.ui.reply;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.common.irecyclerview.IRecyclerView;
import xyz.dcme.agg.common.irecyclerview.OnLoadMoreListener;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.util.AccountUtils;

public class ReplyFragment extends BaseFragment implements ReplyContract.View,
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final String KEY_USER_NAME = "key_username";
    private IRecyclerView mReplyList;
    private SwipeRefreshLayout mSwipeRefresh;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    private String mUserName;
    private ReplyContract.Presenter mPresenter;
    private List<Reply> mData;
    private int mNextPage = 2;

    public static Fragment newInstance(String userName) {
        Fragment fragment = new ReplyFragment();
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
        mReplyList = (IRecyclerView) mRootView.findViewById(R.id.reply_list);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mSwipeRefresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.reply_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        initToolbar();
        initRecycle();

        mPresenter.start(mUserName);
    }

    @Override
    public void initPresenter() {
        mPresenter = new ReplyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reply;
    }

    private void initRecycle() {
        mData = new ArrayList<>();

        mReplyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonAdapter<Reply> adapter = new ReplyAdapter(getActivity(),
                R.layout.item_reply, mData);
        mReplyList.setAdapter(adapter);
        mReplyList.setOnLoadMoreListener(this);
    }

    private void initToolbar() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
            ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                int titleResId = AccountUtils.isCurrentAccount(activity, mUserName)
                        ? R.string.reply : R.string.his_reply;
                ab.setTitle(titleResId);
            }
        }
    }

    @Override
    public void showIndicator(final boolean active) {
        int time = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(time).alpha(active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
                    }
                });
        mReplyList.setVisibility(!active ? View.VISIBLE : View.GONE);
        mReplyList.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mReplyList.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showRefresh(List<Reply> replies) {
        mSwipeRefresh.setRefreshing(false);
        mData.clear();
        mData.addAll(replies);
        mReplyList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Reply> data) {
        mData.addAll(data);
        mReplyList.getAdapter().notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void setPresenter(ReplyContract.Presenter presenter) {
        mPresenter = presenter;
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
