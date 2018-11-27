package cn.okclouder.ovc.frag.whatshot;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseRecycleFragment;
import cn.okclouder.ovc.base.BaseRecyclerAdapter;
import cn.okclouder.ovc.base.RecyclerViewHolder;
import cn.okclouder.ovc.frag.home.HomeControllerTopBarFragment;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.hot.HotContract;
import cn.okclouder.ovc.ui.hot.HotPresenter;
import cn.okclouder.ovc.ui.post.OnRvItemListener;
import cn.okclouder.library.util.ImageLoader;

public class WhatsHotFragment extends HomeControllerTopBarFragment implements HotContract.View {
    private List<Post> mData = new ArrayList<>();
    private HotContract.Presenter mPresenter;
    private WhatsHotRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void initData() {
        mData.clear();
        mPresenter = new HotPresenter(this);
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mRecyclerAdapter = new WhatsHotRecyclerAdapter(getActivity(), mData);
        return mRecyclerAdapter;
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
        return R.string.whats_hot;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.refresh();
    }

    @Override
    public void setPresenter(HotContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Post> data) {
        mRecyclerAdapter.clearAndAddAll(data);
    }

    @Override
    public void showIndicator(boolean isActive) {
        if (!isActive) {
            mPullRefreshLayout.finishRefresh();
            showEmptyView(BaseRecycleFragment.EMPTY_VIEW_TYPE.NORMAL);
        }
    }

    @Override
    public void showError() {
        showEmptyView(BaseRecycleFragment.EMPTY_VIEW_TYPE.ERROR);
    }

    private static class WhatsHotRecyclerAdapter extends BaseRecyclerAdapter<Post> {
        private final Context mContext;

        public WhatsHotRecyclerAdapter(Context ctx, List<Post> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_whats_hot;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Post post) {
            holder.setText(R.id.post_content, post.title);
            holder.setText(R.id.post_user_name, post.userName);

            ImageView iv = holder.getImageView(R.id.post_avatar);
            ImageLoader.displayCircle(mContext, iv, post.avatarUrl);

            holder.setClickListener(R.id.post_item, new OnRvItemListener(mContext, post));
        }
    }
}
