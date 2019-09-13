package cn.okclouder.ovc.frag.explore;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.ImageLoader;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseRecycleFragment;
import cn.okclouder.ovc.base.BaseRecyclerAdapter;
import cn.okclouder.ovc.base.RecyclerViewHolder;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.node.Node;
import cn.okclouder.ovc.ui.node.NodeListContract;
import cn.okclouder.ovc.ui.node.NodeListPresenter;
import cn.okclouder.ovc.ui.post.OnRvItemListener;
import cn.okclouder.ovc.util.Constants;


public class ExploreTopicFragment extends BaseRecycleFragment implements NodeListContract.View {
    private List<Post> mData = new ArrayList<>();
    private NodeListContract.Presenter mPresenter;
    private int mNextPage = 2;
    private ExploreTopicAdapter mRecyclerAdapter;
    private String mNodeName;

    public static Fragment newInstance(Node node) {
        ExploreTopicFragment fragment = new ExploreTopicFragment();
        Bundle args = new Bundle();
        args.putString(Constants.NODE_NAME, node.getName());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if (null != args) {
            mNodeName = args.getString(Constants.NODE_NAME);
        }

        mData.clear();
        mPresenter = new NodeListPresenter(this);
    }

    @Override
    protected void autoRefresh() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.start(mNodeName);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.load(mNodeName, mNextPage);
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mRecyclerAdapter = new ExploreTopicAdapter(getActivity(), mData);
        return mRecyclerAdapter;
    }

    @Override
    public void setPresenter(NodeListContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void startLogin() {

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

    @Override
    public void showLoginTips() {
        showEmptyView(EMPTY_VIEW_TYPE.LOGIN);
    }

    private class ExploreTopicAdapter extends BaseRecyclerAdapter<Post> {
        private final Context mContext;

        public ExploreTopicAdapter(Context ctx, List<Post> list) {
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
