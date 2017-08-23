package xyz.dcme.agg.ui.notify;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.main.MainActivity;
import xyz.dcme.library.base.BaseFragment;

public class MessageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener, MessageContract.View {
    public static final String LOG_TAG = "MessageFragment";
    private SwipeRefreshLayout mRefreshLayout;
    private IRecyclerView mList;
    private Toolbar mToolbar;
    private List<Message> mData = new ArrayList<>();
    private MessageAdapter mAdapter;
    private MessageContract.Presenter mPresenter;
    private int mNextPage = 2;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.msg_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mList = (IRecyclerView) mRootView.findViewById(R.id.msg_list);
        mList.setOnLoadMoreListener(this);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MessageAdapter(getActivity(), R.layout.item_message, mData);
        mList.setAdapter(mAdapter);

        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        initToolbar(mToolbar, R.string.message, R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });

        onRefresh();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MessagePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
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
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(messages);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Message> messages) {
        mAdapter.getDatas().addAll(messages);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void showIndicator(boolean isActive) {
        mRefreshLayout.setRefreshing(isActive);
    }
}
