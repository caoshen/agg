package cn.okclouder.ovc.frag.home;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseRecycleFragment;
import cn.okclouder.ovc.base.BaseRecyclerAdapter;
import cn.okclouder.ovc.base.NetworkUtils;

public abstract class HomeControllerTopBarFragment extends HomeControllerFragment implements QMUIPullRefreshLayout.OnPullListener {
    protected QMUIPullRefreshLayout mPullRefreshLayout;
    protected IRecyclerView mIRecyclerView;
    private QMUITopBar mTopbar;
    protected QMUIEmptyView mEmptyView;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recommend_recycle_with_top_bar, null);
        initData();
        initViews(rootView);
        return rootView;
    }

    protected abstract void initData();

    protected void initViews(View rootView) {
        mTopbar = rootView.findViewById(R.id.topbar);
        mTopbar.setTitle(getTopBarTitleStrId());

        mPullRefreshLayout = rootView.findViewById(R.id.pull_to_refresh);
        mPullRefreshLayout.setOnPullListener(this);

        mIRecyclerView = rootView.findViewById(R.id.recycler);
        mIRecyclerView.setAdapter(getItemAdapter());
        mIRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIRecyclerView.addItemDecoration(getItemDecoration());

        mEmptyView = rootView.findViewById(R.id.emptyView);

//        checkNetwork();
    }

    protected void showEmptyView(BaseRecycleFragment.EMPTY_VIEW_TYPE type) {
        if (type == BaseRecycleFragment.EMPTY_VIEW_TYPE.ERROR) {
            if (!NetworkUtils.isConnected(getActivity())) {
                mEmptyView.show(false, getString(R.string.load_fail), getString(R.string.error_network),
                        getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                autoRefresh();
                            }
                        });
            }
            mPullRefreshLayout.setVisibility(View.GONE);
        } else if (type == BaseRecycleFragment.EMPTY_VIEW_TYPE.LOADING){
            mEmptyView.show(true);
            mPullRefreshLayout.setVisibility(View.GONE);
        } else if (type == BaseRecycleFragment.EMPTY_VIEW_TYPE.NORMAL) {
            mEmptyView.hide();
            mPullRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    private void checkNetwork() {
        if (NetworkUtils.isConnected(getActivity())) {
            mEmptyView.hide();
            mPullRefreshLayout.setVisibility(View.VISIBLE);
            autoRefresh();
        } else {
            mEmptyView.show(false, getString(R.string.load_fail), getString(R.string.error_network),
                    getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkNetwork();
                        }
                    });
            mPullRefreshLayout.setVisibility(View.GONE);
        }
    }

    protected abstract void autoRefresh();

    protected abstract int getTopBarTitleStrId();

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = QMUIDisplayHelper.dp2px(getActivity(), 6);
            }
        };
    }

    @Override
    public void onMoveTarget(int offset) {

    }

    @Override
    public void onMoveRefreshView(int offset) {

    }

    @Override
    public void onRefresh() {

    }

    protected abstract BaseRecyclerAdapter getItemAdapter();

}
