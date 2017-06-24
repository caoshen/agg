package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.postdetail.data.PostComment;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.ShareUtils;
import xyz.dcme.agg.widget.BottomSheetBar;

public class PostDetailFragment extends BaseFragment implements PostDetailContract.View,
        View.OnClickListener {
    public static final String KEY_ARG_URL = "arg_url";
    private static final int REQUEST_LOGIN = 1000;
    private PostDetailContract.Presenter mPresenter;
    private PostDetailAdapter mAdapter;
    private List<PostDetailItem> mData = new ArrayList<>();
    private RecyclerView mRecycler;
    private String mUrl;
    private ProgressBar mLoadingProgressBar;
    private BottomSheetBar mBottomBar;

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
        mRecycler = (RecyclerView) mRootView.findViewById(R.id.post_detail);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(lm);
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mAdapter = new PostDetailAdapter(getActivity(), mData);
        mAdapter.addItemViewDelegate(new PostContentDelegate(getActivity()));
        PostMyCommentDelegate myCommentDelegate = new PostMyCommentDelegate(getActivity());
        mBottomBar = BottomSheetBar.delegation(getActivity());
        mBottomBar.setSendCommentListener(this);
        mAdapter.addItemViewDelegate(myCommentDelegate);
        mAdapter.addItemViewDelegate(new PostCommentDelegate(getActivity()));


        mRecycler.setAdapter(mAdapter);
        mLoadingProgressBar = (ProgressBar) mRootView.findViewById(R.id.loading);

        initToolbar(mRootView);
        initBottomBar(mRootView);
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
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mUrl)) {
            mPresenter.loadDetail(mUrl);
        }
    }

    @Override
    public void showIndicator(boolean isActive) {
        if (isActive) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            mLoadingProgressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void onRefresh(List<PostDetailItem> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<PostDetailItem> data) {

    }

    @Override
    public void addComment(String comment) {
        String rightNow = getString(R.string.right_now);
        PostDetailItem item = new PostComment("visitor", "", comment, rightNow);
        int size = mAdapter.getItemCount();
        mAdapter.getDatas().add(item);
        mAdapter.notifyItemInserted(size);
        mRecycler.scrollToPosition(size);
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
        }
        return super.onOptionsItemSelected(item);
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
}
