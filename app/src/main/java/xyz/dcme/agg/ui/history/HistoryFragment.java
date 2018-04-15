package xyz.dcme.agg.ui.history;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_history) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.clear_history)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearAllHistory();
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void clearAllHistory() {
        HistoryDbHelper.getInstance().deleteAllHistory(getContext());
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }

}
