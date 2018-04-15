package xyz.dcme.agg.frag.history;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseRecycleTopBarFragment;
import xyz.dcme.agg.base.BaseRecyclerAdapter;
import xyz.dcme.agg.base.RecyclerViewHolder;
import xyz.dcme.agg.database.HistoryInfo;
import xyz.dcme.agg.database.helper.HistoryDbHelper;
import xyz.dcme.agg.ui.postdetail.PostDetailActivity;
import xyz.dcme.library.util.ImageLoader;


public class BrowserHistoryFragment extends BaseRecycleTopBarFragment {
    private List<HistoryInfo> mData = new ArrayList<>();
    private BrowserHistoryRecyclerAdapter mAdapter;

    public static BrowserHistoryFragment newInstance() {
        return new BrowserHistoryFragment();
    }

    @Override
    protected void initData() {
        mData.clear();
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        load();
    }

    @Override
    protected void autoRefresh() {
        load();
    }

    @Override
    protected int getTopBarTitleStrId() {
        return R.string.history;
    }

    @Override
    protected BaseRecyclerAdapter getItemAdapter() {
        mAdapter = new BrowserHistoryRecyclerAdapter(getActivity(), mData);
        return mAdapter;
    }

    private void load() {
        // TODO: should in work thread when operate db.
        List<HistoryInfo> historyList = HistoryDbHelper.getInstance().queryHistoryLimit(getActivity(), 20);
        mAdapter.clearAndAddAll(historyList);
        mPullRefreshLayout.finishRefresh();
        // TODO: should delete history > limit.
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected boolean isTopBarBack() {
        return true;
    }

    private static class BrowserHistoryRecyclerAdapter extends BaseRecyclerAdapter<HistoryInfo> {

        private Context mContext;

        public BrowserHistoryRecyclerAdapter(Context ctx, List<HistoryInfo> list) {
            super(ctx, list);
            mContext = ctx;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_whats_hot;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, final HistoryInfo item) {
            holder.setText(R.id.post_content, item.title);
            holder.setText(R.id.post_user_name, item.author);
            ImageView iv = holder.getImageView(R.id.post_avatar);
            ImageLoader.displayCircle(mContext, iv, item.avatar);

            holder.setClickListener(R.id.post_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailActivity.startActivity(mContext, item.link);
                }
            });
        }
    }

}
