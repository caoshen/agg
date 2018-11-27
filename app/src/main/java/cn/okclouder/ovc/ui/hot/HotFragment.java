package cn.okclouder.ovc.ui.hot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.main.MainActivity;
import cn.okclouder.library.base.BaseFragment;


public class HotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
    HotContract.View {

    public static final String LOG_TAG = "HotFragment";

    SwipeRefreshLayout swipe;
    IRecyclerView list;
    private HotListAdapter mAdapter;
    private List<Post> mData = new ArrayList<>();
    private HotContract.Presenter mHotPresenter;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        initToolbar(toolbar, R.string.whats_hot, R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });
        swipe = (SwipeRefreshLayout) mRootView.findViewById(R.id.hot_refresh_layout);
        list = (IRecyclerView) mRootView.findViewById(R.id.hot_list);

        mData.clear();
        mAdapter = new HotListAdapter(getActivity(), R.layout.item_post, mData);
        list.setAdapter(mAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        swipe.setOnRefreshListener(this);

        autoRefresh();
    }

    private void autoRefresh() {
        mHotPresenter.refresh();
    }

    @Override
    protected void initPresenter() {
        new HotPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    public void onRefresh() {
        mHotPresenter.refresh();
    }

    @Override
    public void setPresenter(HotContract.Presenter presenter) {
        mHotPresenter = presenter;
    }

    @Override
    public void showRefresh(List<Post> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIndicator(boolean isActive) {
        swipe.setRefreshing(isActive);
    }

    @Override
    public void showError() {

    }
}
