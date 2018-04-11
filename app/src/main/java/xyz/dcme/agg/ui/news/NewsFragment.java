package xyz.dcme.agg.ui.news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.base.BaseFragment;

public class NewsFragment extends BaseFragment implements
        OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,
        NewsContract.View {

    public static final String LOG_TAG = "NewsFragment";

    SwipeRefreshLayout swipe;
    IRecyclerView list;

    private NewsListAdapter mAdapter;
    private List<Post> mData = new ArrayList<>();
    private NewsContract.Presenter mNewsPresenter;
    private int mNextPage = 2;
    private String mTabName;

    @Override
    protected void getArgs() {
        super.getArgs();
        Bundle args = getArguments();
        mTabName = args.getString(Constants.TAB_NAME);
    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) mRootView.findViewById(R.id.news_refresh_layout);
        list = (IRecyclerView) mRootView.findViewById(R.id.news_list);

        mData.clear();
        mAdapter = new NewsListAdapter(getActivity(), R.layout.item_post, mData);
        list.setAdapter(mAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        swipe.setOnRefreshListener(this);
        list.setOnLoadMoreListener(this);

        autoRefresh();
    }

    private void autoRefresh() {
        mNewsPresenter.refresh(mTabName);
    }

    @Override
    protected void initPresenter() {
        new NewsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onLoadMore() {
        mNewsPresenter.load(mTabName, mNextPage);
    }

    @Override
    public void onRefresh() {
        mNewsPresenter.refresh(mTabName);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mNewsPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Post> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoad(List<Post> data) {
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void showIndicator(boolean isActive) {
        swipe.setRefreshing(isActive);
    }

    @Override
    public void showError() {

    }

    public static NewsFragment newInstance(String name) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TAB_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }
}
