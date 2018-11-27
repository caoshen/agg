package cn.okclouder.ovc.frag.recommend;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseRecycleFragment;
import cn.okclouder.ovc.base.BaseRecyclerAdapter;
import cn.okclouder.ovc.base.RecyclerViewHolder;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.news.NewsContract;
import cn.okclouder.ovc.ui.news.NewsPresenter;
import cn.okclouder.ovc.ui.post.OnRvItemListener;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.library.util.ImageLoader;

public class RecommendFragment extends BaseRecycleFragment implements NewsContract.View {
    private List<Post> mData = new ArrayList<>();
    private NewsContract.Presenter mNewsPresenter;
    private int mNextPage = 2;
    private String mTabName;
    private RecommendRecyclerAdapter mRecyclerAdapter;

    public static RecommendFragment newInstance(String name) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TAB_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mRecyclerAdapter = new RecommendRecyclerAdapter(getActivity(), mData);
        return mRecyclerAdapter;
    }

    @Override
    protected void initData() {
        mData.clear();
        mNewsPresenter = new NewsPresenter(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTabName = arguments.getString(Constants.TAB_NAME);
        }
    }

    @Override
    protected void autoRefresh() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mNewsPresenter.refresh(mTabName);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mNewsPresenter.load(mTabName, mNextPage);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mNewsPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Post> data) {
        mRecyclerAdapter.clearAndAddAll(data);
    }

    @Override
    public void showLoad(List<Post> data) {
        mRecyclerAdapter.addAll(data);
        mNextPage++;
    }

    @Override
    public void showIndicator(boolean isActive) {
        if (!isActive) {
            mPullRefreshLayout.finishRefresh();
            showEmptyView(EMPTY_VIEW_TYPE.NORMAL);
        }
    }

    @Override
    public void showError() {
        showEmptyView(EMPTY_VIEW_TYPE.ERROR);
    }

    private static class RecommendRecyclerAdapter extends BaseRecyclerAdapter<Post> {
        private final Context mContext;

        public RecommendRecyclerAdapter(Context ctx, List<Post> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_recommend_article;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Post post) {
            holder.setText(R.id.post_content, post.title);
            holder.setText(R.id.post_user_name, post.userName);
            holder.setText(R.id.post_last_visit_time, post.lastVisitTime);

            if (TextUtils.isEmpty(post.commentCount)) {
                post.commentCount = "0";
            }
            String commentCount = "" + post.commentCount;
            holder.setText(R.id.post_comment_count, commentCount);

            ImageView iv = holder.getImageView(R.id.post_avatar);
            ImageLoader.displayCircle(mContext, iv, post.avatarUrl);

            holder.setClickListener(R.id.post_item, new OnRvItemListener(mContext, post));
        }
    }

}
