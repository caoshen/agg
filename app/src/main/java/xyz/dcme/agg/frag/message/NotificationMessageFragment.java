package xyz.dcme.agg.frag.message;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseRecycleFragment;
import xyz.dcme.agg.base.BaseRecycleTopBarFragment;
import xyz.dcme.agg.base.BaseRecyclerAdapter;
import xyz.dcme.agg.base.RecyclerViewHolder;
import xyz.dcme.agg.frag.article.ArticleActivity;
import xyz.dcme.agg.ui.notify.Message;
import xyz.dcme.agg.ui.notify.MessageContract;
import xyz.dcme.agg.ui.notify.MessagePresenter;

public class NotificationMessageFragment extends BaseRecycleTopBarFragment implements MessageContract.View {
    public static final String LOG_TAG = NotificationMessageFragment.class.getSimpleName();
    private List<Message> mData = new ArrayList<>();
    private MessageContract.Presenter mPresenter;
    private int mNextPage = 2;
    private View mRootView;
    private NotificationRecyclerAdapter mAdapter;

    public static NotificationMessageFragment newInstance() {
        return new NotificationMessageFragment();
    }

    @Override
    protected void initData() {
        mData.clear();
        mPresenter = new MessagePresenter(this);
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        onRefresh();
    }

    @Override
    protected boolean isTopBarBack() {
        return true;
    }

    @Override
    protected void autoRefresh() {
        onRefresh();
    }

    @Override
    protected int getTopBarTitleStrId() {
        return R.string.message;
    }

    protected void initPresenter() {
        mPresenter = new MessagePresenter(this);
    }


    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mAdapter = new NotificationRecyclerAdapter(getActivity(), mData);
        return mAdapter;
    }

    @Override
    public void onLoadMore() {
        mPresenter.load(mNextPage);
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Message> messages) {
        mAdapter.clearAndAddAll(messages);
    }

    @Override
    public void showLoad(List<Message> messages) {
        mAdapter.addAll(messages);
        mNextPage++;
    }

    @Override
    public void showIndicator(boolean isActive) {
        if (!isActive) {
            mPullRefreshLayout.finishRefresh();
            showEmptyView(BaseRecycleFragment.EMPTY_VIEW_TYPE.NORMAL);
        }
    }

    private static class NotificationRecyclerAdapter extends BaseRecyclerAdapter<Message> {

        private final Context mContext;

        public NotificationRecyclerAdapter(Context ctx, List<Message> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_notification_message;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, final Message item) {
            holder.setText(R.id.notification_message_title, item.getTitle());
            holder.setText(R.id.notification_message_content, item.getContent());

            holder.setClickListener(R.id.post_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleActivity.startActivity(mContext, item.getLink());
                }
            });
        }
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected boolean isLoadMoreEnabled() {
        return true;
    }
}
