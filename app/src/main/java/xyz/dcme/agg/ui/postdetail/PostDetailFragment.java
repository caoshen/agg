package xyz.dcme.agg.ui.postdetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.common.irecyclerview.IRecyclerView;
import xyz.dcme.agg.common.irecyclerview.OnLoadMoreListener;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.ui.publish.PublishActivity;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.ShareUtils;
import xyz.dcme.agg.widget.BottomSheetBar;

public class PostDetailFragment extends BaseFragment implements PostDetailContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
        OnCommentListener {
    public static final String KEY_ARG_URL = "arg_url";
    private static final int REQUEST_LOGIN = 1000;
    private PostDetailContract.Presenter mPresenter;
    private PostDetailAdapter mAdapter;
    private List<PostDetailItem> mData = new ArrayList<>();
    private IRecyclerView mRecycler;
    private String mUrl;
    private ProgressBar mLoadingProgressBar;
    private BottomSheetBar mBottomBar;
    private SwipeRefreshLayout mRefreshLayout;
    private int mNextPage = 2;
    private ImageButton mInsertImage;

    public static PostDetailFragment newInstance(String url) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(PostDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(KEY_ARG_URL);
        }
        setHasOptionsMenu(true);
    }

    @Override
    protected void initView() {
        mRecycler = (IRecyclerView) mRootView.findViewById(R.id.post_detail);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(lm);
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecycler.setOnLoadMoreListener(this);

        mAdapter = new PostDetailAdapter(getActivity(), mData);
        mAdapter.addItemViewDelegate(new PostContentDelegate(getActivity()));
        PostMyCommentDelegate myCommentDelegate = new PostMyCommentDelegate(getActivity());
        mBottomBar = BottomSheetBar.delegation(getActivity());
        mBottomBar.setSendCommentListener(this);
        mAdapter.addItemViewDelegate(myCommentDelegate);
        mAdapter.addItemViewDelegate(new PostCommentDelegate(getActivity(), this));

        mRecycler.setAdapter(mAdapter);
        mLoadingProgressBar = (ProgressBar) mRootView.findViewById(R.id.loading);

        initToolbar(mRootView);
        initBottomBar(mRootView);
        initRefresh(mRootView);
        initAddImage(mRootView);

        mPresenter.start(mUrl);
    }

    private void initAddImage(View rootView) {
        mInsertImage = (ImageButton) rootView.findViewById(R.id.post_insert_photo);
        mInsertImage.setOnClickListener(this);
    }

    private void initRefresh(View rootView) {
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.post_detail_refresh);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initBottomBar(View rootView) {
        View commentBar = rootView.findViewById(R.id.item_send_comment);
        commentBar.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PostDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void showIndicator(final boolean isActive) {
        mLoadingProgressBar.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLoadingProgressBar.animate()
                .setDuration(200)
                .alpha(isActive ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingProgressBar.setVisibility(isActive ? View.VISIBLE : View.GONE);
                    }
                });
        mRecycler.setVisibility(!isActive ? View.VISIBLE : View.GONE);
        mRecycler.animate()
                .setDuration(200)
                .alpha(!isActive ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRecycler.setVisibility(!isActive ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showRefreshingIndicator(boolean isActive) {
        mRefreshLayout.setRefreshing(isActive);
    }

    @Override
    public void showRefresh(List<PostDetailItem> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<PostDetailItem> data) {
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void sendCommentFailed() {
        mBottomBar.hide();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, R.string.send_comment_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCommentIndicator(boolean b) {
        mBottomBar.sendingComment(b);
    }

    @Override
    public void setCommentSuccess() {
        mBottomBar.hide();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, R.string.send_comment_success, Toast.LENGTH_SHORT).show();
        }
        onRefresh();
    }

    @Override
    public void startLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button) {
            String comment = mBottomBar.getComment();
            if (!TextUtils.isEmpty(comment)) {
                mPresenter.sendReply(comment, mUrl);
            }
        } else if (v.getId() == R.id.item_send_comment) {
            mBottomBar.show();
        } else if (v.getId() == R.id.post_insert_photo) {
            PublishActivity.startPublish(getActivity(), mUrl);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_post_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_post_share) {
            startShare();
            return true;
        } else if (item.getItemId() == R.id.item_post_browser) {
            startBrowser();
            return true;
        } else if (item.getItemId() == R.id.item_test_crash) {
            CrashReport.testJavaCrash();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(Constants.HOME_URL + mUrl));
        startActivity(intent);
    }

    private void startShare() {
        List<PostDetailItem> datas = mAdapter.getDatas();
        if (datas != null && !datas.isEmpty()) {
            PostDetailItem postDetailItem = datas.get(0);
            if (postDetailItem instanceof PostContent) {
                String title = ((PostContent) postDetailItem).getTitle();
                ShareUtils.shareText(getActivity(), title, Constants.HOME_URL + mUrl);
            }
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mUrl);
    }

    @Override
    public void onLoadMore(View view) {
        mPresenter.load(mUrl, mNextPage);
    }

    @Override
    public void onCommentToFloor(String comment) {
        mBottomBar.show();
        mBottomBar.setComment(comment);
    }
}
