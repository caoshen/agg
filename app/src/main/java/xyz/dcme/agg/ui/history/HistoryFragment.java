package xyz.dcme.agg.ui.history;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.HistoryInfo;
import xyz.dcme.agg.database.helper.HistoryDbHelper;
import xyz.dcme.agg.ui.main.MainActivity;
import xyz.dcme.library.base.BaseFragment;


public class HistoryFragment extends BaseFragment implements OnLoadMoreListener {
    public static final String LOG_TAG = "HistoryFragment";
    private IRecyclerView mList;
    private Toolbar mToolbar;
    private List<HistoryInfo> mData = new ArrayList<>();
    private HistoryAdapter mAdapter;

    @Override
    protected void initView() {
        mList = (IRecyclerView) mRootView.findViewById(R.id.history_list);
        mList.setOnLoadMoreListener(this);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HistoryAdapter(getActivity(), R.layout.item_post, mData);
        mList.setAdapter(mAdapter);

        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        initToolbar(mToolbar, R.string.history, R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        mData.clear();
        List<HistoryInfo> historyList = HistoryDbHelper.getInstance().queryAllHistory(getActivity());
        mData.addAll(historyList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onLoadMore() {

    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }
}
