package xyz.dcme.agg.frag.favourite;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseRecycleFragment;
import xyz.dcme.agg.base.BaseRecycleTopBarFragment;
import xyz.dcme.agg.base.BaseRecyclerAdapter;
import xyz.dcme.agg.base.RecyclerViewHolder;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.favorite.FavoriteContract;
import xyz.dcme.agg.ui.favorite.FavoritePresenter;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.library.util.ImageLoader;


public class FavouriteArticleFragment extends BaseRecycleTopBarFragment implements FavoriteContract.View {
    private String mUserName;
    private FavoriteContract.Presenter mPresenter;
    private List<Post> mData = new ArrayList<>();
    private int mNextPage = 2;
    private static final String KEY_FAVOURITE_USER_NAME = "key_favourite_user_name";
    private FavouriteRecyclerAdapter mAdapter;

    public static FavouriteArticleFragment newInstance(String username) {
        FavouriteArticleFragment fragment = new FavouriteArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FAVOURITE_USER_NAME, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        mData.clear();
        mPresenter = new FavoritePresenter(this);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(KEY_FAVOURITE_USER_NAME);
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        onRefresh();
    }

    @Override
    protected void autoRefresh() {
        onRefresh();
    }

    @Override
    protected int getTopBarTitleStrId() {
        return R.string.favourite;
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mAdapter = new FavouriteRecyclerAdapter(getActivity(), mData);
        return mAdapter;
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showIndicator(boolean active) {
        if (!active) {
            mPullRefreshLayout.finishRefresh();
            showEmptyView(BaseRecycleFragment.EMPTY_VIEW_TYPE.NORMAL);
        }
    }

    @Override
    public void showRefresh(List<Post> data) {
        mAdapter.clearAndAddAll(data);
    }

    @Override
    public void showLoad(List<Post> data) {
        mAdapter.addAll(data);
        mNextPage++;
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected boolean isTopBarBack() {
        return true;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.refresh(mUserName);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.load(mUserName, mNextPage);
    }

    private static class FavouriteRecyclerAdapter extends BaseRecyclerAdapter<Post> {

        private Context mContext;

        public FavouriteRecyclerAdapter(Context ctx, List<Post> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_recommend_article;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Post item) {
            holder.setText(R.id.post_content, item.title);
            holder.setText(R.id.post_user_name, item.userName);
            holder.setText(R.id.post_last_visit_time, item.lastVisitTime);

            if (TextUtils.isEmpty(item.commentCount)) {
                item.commentCount = "0";
            }
            String commentCount = "" + item.commentCount;
            holder.setText(R.id.post_comment_count, commentCount);

            ImageView iv = holder.getImageView(R.id.post_avatar);
            ImageLoader.displayCircle(mContext, iv, item.avatarUrl);

            holder.setClickListener(R.id.post_item, new PostCommonAdapter.OnRvItemListener(mContext, item));
        }
    }
}
