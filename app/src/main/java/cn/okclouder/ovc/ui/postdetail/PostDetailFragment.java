package cn.okclouder.ovc.ui.postdetail;

import android.app.Activity;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.AccountManager;
import cn.okclouder.account.ErrorStatus;
import cn.okclouder.account.LoginHandler;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.common.irecyclerview.IRecyclerView;
import cn.okclouder.ovc.common.irecyclerview.OnLoadMoreListener;
import cn.okclouder.ovc.ui.postdetail.data.PostContent;
import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;
import cn.okclouder.ovc.ui.publish.PublishActivity;
import cn.okclouder.ovc.util.AnimationUtils;
import cn.okclouder.ovc.util.ClipBoardUtils;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.PostUtils;
import cn.okclouder.ovc.util.ShareUtils;
import cn.okclouder.ovc.widget.BottomMenu;
import cn.okclouder.ovc.widget.BottomSheetBar;
import cn.okclouder.library.base.BaseFragment;
import cn.okclouder.library.widget.appreciateview.AppreciateView;

public class PostDetailFragment extends BaseFragment implements PostDetailContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
        OnCommentListener, BottomMenu.OnMenuItemSelectCallback {
    public static final String KEY_ARG_URL = "arg_url";
    private static final int REQUEST_LOGIN = 1000;
    private PostDetailContract.Presenter mPresenter;
    private PostDetailAdapter mAdapter;
    private List<PostDetailItem> mData = new ArrayList<>();
    private IRecyclerView mRecycler;
    private String mUrl;
    private ProgressBar mProgressBar;
    private BottomSheetBar mBottomBar;
    private SwipeRefreshLayout mRefreshLayout;
    private int mNextPage = 2;
    private FrameLayout mInsertImage;
    private AppreciateView mLikeImage;

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
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progressbar);

        initToolbar(mRootView);
        initBottomBar(mRootView);
        initRefresh(mRootView);
        initAddImage(mRootView);
        initLike(mRootView);

        mPresenter.start(mUrl);
    }

    private void initLike(View rootView) {
        mLikeImage = (AppreciateView) rootView.findViewById(R.id.like_button);
        mLikeImage.setOnCheckedChangeListener(new AppreciateView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(View view, boolean isChecked) {
                if (isChecked) {
                    String url = Constants.VOTE + PostUtils.getUid(mUrl);
                    mPresenter.like(url);
                }
            }
        });
    }

    private void initAddImage(View rootView) {
        mInsertImage = (FrameLayout) rootView.findViewById(R.id.post_insert_photo);
        mInsertImage.setOnClickListener(this);
    }

    private void initRefresh(View rootView) {
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.post_detail_refresh);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initBottomBar(View rootView) {
        View commentBar = rootView.findViewById(R.id.comment_bar);
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
        AnimationUtils.showProgress(mProgressBar, mRefreshLayout, isActive);
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

        if (!data.isEmpty()) {
            PostDetailItem postDetailItem = data.get(0);
            mLikeImage.setChecked(postDetailItem.getAppreciated() == 1);
        }
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
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivityForResult(intent, REQUEST_LOGIN);
        AccountManager.getAccount(getActivity(), new LoginHandler() {
            @Override
            public void onLogin(AccountInfo account) {
                FragmentActivity activity = getActivity();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }

            @Override
            public void onError(ErrorStatus status) {
                FragmentActivity activity = getActivity();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showFavouriteAddTips(String tips) {
        Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPostLike(String tips) {
        Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button) {
            String comment = mBottomBar.getComment();
            if (!TextUtils.isEmpty(comment)) {
                mPresenter.sendComment(comment, mUrl);
            }
        } else if (v.getId() == R.id.comment_bar) {
            mBottomBar.show();
        } else if (v.getId() == R.id.post_insert_photo) {
            PublishActivity.startPublish(getActivity(), Constants.HOME_URL + mUrl);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_post_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_bottom_menu) {
            popMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void popMenu() {
        BottomMenu menu = BottomMenu.delegation(getActivity());
        menu.setMenuListener(this);
        menu.show();
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

    @Override
    public void onReplyVote(String url) {
        mPresenter.like(url);
    }

    @Override
    public void onShare() {
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
    public void onFavourite() {
        // TODO font size
        String url = Constants.ADD_FAVOURITE + PostUtils.getUid(mUrl);
        mPresenter.addFavourite(url);
    }

    @Override
    public void onOpenInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(Constants.HOME_URL + mUrl));
        startActivity(intent);
    }

    @Override
    public void onCopyLink() {
        ClipBoardUtils.copyText(getActivity(), Constants.HOME_URL + mUrl);
        Toast.makeText(getActivity(), R.string.has_copy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == Activity.RESULT_CANCELED) {
            FragmentActivity activity = getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}