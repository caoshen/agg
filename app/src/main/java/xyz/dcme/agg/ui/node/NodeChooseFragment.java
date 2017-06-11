package xyz.dcme.agg.ui.node;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.widget.ItemDragHelperCallback;

public class NodeChooseFragment extends BaseFragment
        implements NodeChooseContract.View {

    public static final int ITEM_PER_LINE = 4;
    private Toolbar mToolbar;
    private RecyclerView mCurNodeList;
    private RecyclerView mMoreNodeList;
    private NodeChoosePresenter mPresenter;
    private NodeChooseAdapter mCurNodeAdapter;
    private NodeChooseAdapter mMoreNodeAdapter;

    @Override
    public void setPresenter(NodeChooseContract.Presenter presenter) {

    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showCurNode(List<Node> nodes) {
        mCurNodeAdapter = new NodeChooseAdapter(getActivity(), R.layout.item_node_choose, nodes);
        mCurNodeList.setLayoutManager(new GridLayoutManager(getActivity(), ITEM_PER_LINE, LinearLayoutManager.VERTICAL,
                false));
        mCurNodeList.setItemAnimator(new DefaultItemAnimator());
        mCurNodeList.setAdapter(mCurNodeAdapter);
        mCurNodeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Node node = mCurNodeAdapter.getDatas().get(position);
                mCurNodeAdapter.getDatas().remove(position);
                mCurNodeAdapter.notifyDataSetChanged();
                mMoreNodeAdapter.getDatas().add(node);
                mMoreNodeAdapter.notifyDataSetChanged();
                mPresenter.onItemChange(mCurNodeAdapter.getDatas(), mMoreNodeAdapter.getDatas());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        ItemDragHelperCallback callback = new ItemDragHelperCallback(mCurNodeAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mCurNodeList);
    }

    @Override
    public void showMoreNode(List<Node> nodes) {
        mMoreNodeAdapter = new NodeChooseAdapter(getActivity(), R.layout.item_node_choose, nodes);
        mMoreNodeList.setLayoutManager(new GridLayoutManager(getActivity(), ITEM_PER_LINE, LinearLayoutManager.VERTICAL,
                false));
        mMoreNodeList.setItemAnimator(new DefaultItemAnimator());
        mMoreNodeList.setAdapter(mMoreNodeAdapter);
        mMoreNodeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Node node = mMoreNodeAdapter.getDatas().get(position);
                mMoreNodeAdapter.getDatas().remove(position);
                mMoreNodeAdapter.notifyDataSetChanged();
                mCurNodeAdapter.getDatas().add(node);
                mCurNodeAdapter.notifyDataSetChanged();
                mPresenter.onItemChange(mCurNodeAdapter.getDatas(), mMoreNodeAdapter.getDatas());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAfterTransition();
                } else {
                    getActivity().finish();
                }
            }
        });
        mToolbar.setTitle(R.string.node_choose);

        mCurNodeList = (RecyclerView) mRootView.findViewById(R.id.node_choose_cur_list);
        mMoreNodeList = (RecyclerView) mRootView.findViewById(R.id.node_choose_more_list);

        mPresenter.load();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new NodeChoosePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_node_choose;
    }

}
