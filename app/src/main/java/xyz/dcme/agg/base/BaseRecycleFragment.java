package xyz.dcme.agg.base;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import xyz.dcme.account.AccountInfo;
import xyz.dcme.account.AccountManager;
import xyz.dcme.account.ErrorStatus;
import xyz.dcme.account.LoginHandler;
import xyz.dcme.agg.R;

public abstract class BaseRecycleFragment extends BaseFragment implements QMUIPullRefreshLayout.OnPullListener, OnLoadMoreListener {
    protected QMUIPullRefreshLayout mPullRefreshLayout;
    protected IRecyclerView mIRecyclerView;
    protected QMUIEmptyView mEmptyView;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recommend_recycle, null);
        initData();
        initViews(rootView);
        return rootView;
    }

    protected abstract void initData();

    protected void initViews(View rootView) {
        mPullRefreshLayout = rootView.findViewById(R.id.pull_to_refresh);
        mPullRefreshLayout.setOnPullListener(this);

        mIRecyclerView = rootView.findViewById(R.id.recycler);
        mIRecyclerView.setAdapter(getItemAdapter());
        mIRecyclerView.setOnLoadMoreListener(this);
        mIRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIRecyclerView.addItemDecoration(getItemDecoration());

        mEmptyView = rootView.findViewById(R.id.emptyView);

//        checkNetwork();
        autoRefresh();
    }

    protected void showEmptyView(EMPTY_VIEW_TYPE type) {
        if (type == EMPTY_VIEW_TYPE.ERROR) {
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
        } else if (type == EMPTY_VIEW_TYPE.LOADING) {
            mEmptyView.show(true);
            mPullRefreshLayout.setVisibility(View.GONE);
        } else if (type == EMPTY_VIEW_TYPE.NORMAL) {
            mEmptyView.hide();
            mPullRefreshLayout.setVisibility(View.VISIBLE);
        } else if (type == EMPTY_VIEW_TYPE.LOGIN) {
            mEmptyView.show(false, getString(R.string.login_please), getString(R.string.login_reason),
                    getString(R.string.action_sign_in), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AccountManager.getAccount(getActivity(), new LoginHandler() {
                                @Override
                                public void onLogin(AccountInfo account) {
                                    autoRefresh();
                                }

                                @Override
                                public void onError(ErrorStatus status) {

                                }
                            });
                        }
                    });
            mPullRefreshLayout.finishRefresh();
            mPullRefreshLayout.setVisibility(View.GONE);
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

    @Override
    public void onLoadMore() {

    }

    public enum EMPTY_VIEW_TYPE {
        LOADING,
        ERROR,
        NORMAL,
        LOGIN;
    }

}
