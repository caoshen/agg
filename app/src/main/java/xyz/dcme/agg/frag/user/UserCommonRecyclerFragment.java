package xyz.dcme.agg.frag.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import xyz.dcme.agg.ui.post.OnRvItemListener;
import xyz.dcme.agg.ui.reply.OnReplyClickListener;
import xyz.dcme.agg.ui.reply.Reply;
import xyz.dcme.library.util.ImageLoader;

public class UserCommonRecyclerFragment extends BaseRecycleTopBarFragment implements UserCommonContract.View {
    public static final String TAG = UserCommonRecyclerFragment.class.getSimpleName();
    private static final String KEY_USER_NAME = "key_user_name";
    private static final java.lang.String KEY_USER_ACTION = "key_user_action";
    private USER_ACTION mAction = USER_ACTION.TOPIC;
    private List<Post> mTopicData = new ArrayList<>();
    private List<Reply> mReplyData = new ArrayList<>();
    private List<Post> mFavData = new ArrayList<>();
    private UserCommonContract.Presenter mPresenter;
    private String mUserName;
    private BaseRecyclerAdapter<Post> mPostAdapter;
    private BaseRecyclerAdapter<Reply> mReplyAdapter;
    private int mNextPage = 2;

    public static UserCommonRecyclerFragment newInstance(String name, int action) {
        UserCommonRecyclerFragment fragment = new UserCommonRecyclerFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER_NAME, name);
        args.putInt(KEY_USER_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(UserCommonContract.Presenter presenter) {
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
        mPostAdapter.clearAndAddAll(data);
    }

    @Override
    public void showLoad(List<Post> data) {
        mPostAdapter.addAll(data);
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

    @Override
    public void showRefreshedReply(List<Reply> data) {
        mReplyAdapter.clearAndAddAll(data);
    }

    @Override
    public void showLoadedReply(List<Reply> data) {
        mReplyAdapter.addAll(data);
        mNextPage++;
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(KEY_USER_NAME);
            int action = args.getInt(KEY_USER_ACTION);
            mAction = USER_ACTION.getAction(action);
        }
    }

    @Override
    protected void initData() {
        mTopicData.clear();
        mReplyData.clear();
        mFavData.clear();
        mPresenter = new UserCommonPresenter(this, mAction);
    }

    @Override
    protected void autoRefresh() {
        onRefresh();
    }

    @Override
    protected boolean isLoadMoreEnabled() {
        return true;
    }

    @Override
    protected int getTopBarTitleStrId() {
        switch (mAction) {
            case TOPIC: {
                return R.string.count_topic;
            }
            case REPLY: {
                return R.string.count_reply;
            }
            case FAVOURITE: {
                return R.string.fav;
            }
            default:
                return R.string.count_topic;
        }
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        switch (mAction) {
            case TOPIC: {
                mPostAdapter = new TopicRecyclerAdapter(getActivity(), mTopicData);
                return mPostAdapter;
            }
            case REPLY: {
                mReplyAdapter = new ReplyRecyclerAdapter(getActivity(), mReplyData);
                return mReplyAdapter;
            }
            case FAVOURITE: {
                mPostAdapter = new FavouriteRecyclerAdapter(getActivity(), mFavData);
                return mPostAdapter;
            }
            default:
                mPostAdapter = new TopicRecyclerAdapter(getActivity(), mTopicData);
                return mPostAdapter;
        }
    }

    public enum USER_ACTION {
        TOPIC,
        REPLY,
        FAVOURITE;

        public static USER_ACTION getAction(int action) {
            switch (action) {
                case 1: {
                    return TOPIC;
                }
                case 2: {
                    return REPLY;
                }
                case 3: {
                    return FAVOURITE;
                }
            }
            return TOPIC;
        }
    }

    private static class TopicRecyclerAdapter extends BaseRecyclerAdapter<Post> {
        private final Context mContext;

        public TopicRecyclerAdapter(Context ctx, List<Post> list) {
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

    private static class ReplyRecyclerAdapter extends BaseRecyclerAdapter<Reply> {
        private final Context mContext;

        public ReplyRecyclerAdapter(Context ctx, List<Reply> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_my_reply;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Reply item) {
            holder.setText(R.id.reply_title, item.title);
            holder.setText(R.id.reply_content, item.content);

            holder.setClickListener(R.id.reply_item, new OnReplyClickListener(mContext, item.detailUrl));
        }
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

            holder.setClickListener(R.id.post_item, new OnRvItemListener(mContext, item));
        }
    }


}
