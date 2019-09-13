package cn.okclouder.ovc.frag.article;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.AccountManager;
import cn.okclouder.account.ErrorStatus;
import cn.okclouder.account.LoginHandler;
import cn.okclouder.library.widget.appreciateview.AppreciateView;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.common.irecyclerview.IRecyclerView;
import cn.okclouder.ovc.common.irecyclerview.OnLoadMoreListener;
import cn.okclouder.ovc.frag.write.WriteActivity;
import cn.okclouder.ovc.ui.postdetail.OnCommentListener;
import cn.okclouder.ovc.ui.postdetail.PostCommentDelegate;
import cn.okclouder.ovc.ui.postdetail.PostContentDelegate;
import cn.okclouder.ovc.ui.postdetail.PostDetailAdapter;
import cn.okclouder.ovc.ui.postdetail.PostDetailContract;
import cn.okclouder.ovc.ui.postdetail.PostDetailPresenter;
import cn.okclouder.ovc.ui.postdetail.PostMyCommentDelegate;
import cn.okclouder.ovc.ui.postdetail.data.PostContent;
import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;
import cn.okclouder.ovc.util.AnimationUtils;
import cn.okclouder.ovc.util.ClipBoardUtils;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.PostUtils;
import cn.okclouder.ovc.util.ShareUtils;
import cn.okclouder.ovc.widget.BottomMenu;
import cn.okclouder.ovc.widget.BottomSheetBar;


public class ArticleFragment extends BaseFragment implements PostDetailContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
        OnCommentListener, BottomMenu.OnMenuItemSelectCallback {

    private static final String KEY_ARGS_URL = "key_args_url";
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
    private QMUIEmptyView mEmptyView;

    private LoginHandler mLoginHandler = new LoginHandler() {
        @Override
        public void onLogin(AccountInfo account) {

        }

        @Override
        public void onError(ErrorStatus status) {

        }
    };
    private RelativeLayout mContentView;


    public static ArticleFragment newInstance(String url) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ARGS_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_article, null);
        initView(view);
        return view;
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
            mUrl = args.getString(KEY_ARGS_URL);
        }
        setHasOptionsMenu(true);
    }

    protected void initView(View view) {
        mRecycler = (IRecyclerView) view.findViewById(R.id.post_detail);
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
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        initBottomBar(view);
        initRefresh(view);
        initAddImage(view);
        initLike(view);
        initTopBar(view);

        initPresenter();
        mPresenter.start(mUrl);

        mEmptyView = view.findViewById(R.id.emptyView);
        mContentView = view.findViewById(R.id.contentView);
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

    protected void initPresenter() {
        mPresenter = new PostDetailPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initTopBar(View view) {
        QMUITopBar topbar = view.findViewById(R.id.topbar);
        topbar.setTitle(R.string.article_detail);
        topbar.addLeftImageButton(cn.okclouder.account.R.drawable.ic_topbar_back_blue, com.qmuiteam.qmui.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
        topbar.addRightImageButton(R.drawable.ic_more_blue, R.id.id_menu_more)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popMenu();
                    }
                });
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
        FragmentActivity activity = getActivity();
        if (activity != null && !TextUtils.isEmpty(tips)) {
            Toast.makeText(activity, tips, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoginTips() {
        mEmptyView.show(false, getString(R.string.login_please), getString(R.string.login_reason),
                getString(R.string.action_sign_in), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountManager.getAccount(getActivity(), new LoginHandler() {
                            @Override
                            public void onLogin(AccountInfo account) {
                                mContentView.setVisibility(View.VISIBLE);
                                mEmptyView.hide();
                                onRefresh();
                            }

                            @Override
                            public void onError(ErrorStatus status) {

                            }
                        });
                    }
                });
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button) {
            if (AccountManager.hasLoginAccount(getActivity())) {
                String comment = mBottomBar.getComment();
                if (!TextUtils.isEmpty(comment)) {
                    mPresenter.sendComment(comment, mUrl);
                }
            } else {
                AccountManager.getAccount(getActivity(), mLoginHandler);
            }
        } else if (v.getId() == R.id.comment_bar) {
            if (AccountManager.hasLoginAccount(getActivity())) {
                mBottomBar.show();
            } else {
                AccountManager.getAccount(getActivity(), mLoginHandler);
            }
        } else if (v.getId() == R.id.post_insert_photo) {
            if (AccountManager.hasLoginAccount(getActivity())) {
                WriteActivity.startPublish(getActivity(), Constants.HOME_URL + mUrl);
            } else {
                AccountManager.getAccount(getActivity(), mLoginHandler);
            }
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
        if (AccountManager.hasLoginAccount(getActivity())) {
            mBottomBar.show();
            mBottomBar.setComment(comment);
        } else {
            AccountManager.getAccount(getActivity(), mLoginHandler);
        }
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
