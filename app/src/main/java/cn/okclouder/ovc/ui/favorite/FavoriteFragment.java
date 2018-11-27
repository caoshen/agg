package cn.okclouder.ovc.ui.favorite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.main.MainActivity;
import cn.okclouder.ovc.util.AccountUtils;
import cn.okclouder.library.base.BaseFragment;

public class FavoriteFragment extends BaseFragment
        implements FavoriteContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final String KEY_USER_NAME = "key_username";
    public static final String LOG_TAG = "FavoriteFragment";
    private static final String KEY_NAV = "key_nav";

    private IRecyclerView mFavList;
    private SwipeRefreshLayout mSwipeRefresh;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    private String mUserName;
    private FavoriteContract.Presenter mPresenter;
    private List<Post> mData;
    private int mNextPage = 2;
    private boolean mIsNav = false;

    public static FavoriteFragment newInstance(String userName, boolean isNav) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER_NAME, userName);
        args.putBoolean(KEY_NAV, isNav);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(KEY_USER_NAME);
            mIsNav = args.getBoolean(KEY_NAV);
        }
    }

    @Override
    protected void initView() {
        mFavList = (IRecyclerView) mRootView.findViewById(R.id.fav_list);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.fav_progress);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mSwipeRefresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.fav_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);

        initToolbar();
        initRecycle();

        mPresenter.start(mUserName);
    }

    @Override
    public void initPresenter() {
        mPresenter = new FavoritePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    private void initRecycle() {
        mData = new ArrayList<>();

        mFavList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavList.setHasFixedSize(true);
        CommonAdapter<Post> adapter = new FavoriteAdapter(getActivity(),
                R.layout.item_post, mData);
        mFavList.setAdapter(adapter);
    }

    private void initToolbar() {
        if (mIsNav) {
            initToolbar(mToolbar, R.string.fav, R.drawable.ic_menu, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).toggleDrawer();
                }
            });
        } else {
            FragmentActivity activity = getActivity();
            if (activity != null && activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
                ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
                if (ab != null) {
                    ab.setDisplayHomeAsUpEnabled(true);
                    int titleResId = AccountUtils.isCurrentAccount(activity, mUserName)
                            ? R.string.favourite : R.string.his_fav;
                    ab.setTitle(titleResId);
                }
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
        mFavList.setVisibility(!active ? View.VISIBLE : View.GONE);
        mFavList.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFavList.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showRefresh(List<Post> posts) {
        mSwipeRefresh.setRefreshing(false);
        mData.clear();
        mData.addAll(posts);
        mFavList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Post> data) {
        mData.addAll(data);
        mFavList.getAdapter().notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(true);
        mPresenter.refresh(mUserName);
    }

    @Override
    public void onLoadMore() {
        mPresenter.load(mUserName, mNextPage);
    }
}
