package xyz.dcme.agg.ui.node;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.NodeDbHelper;
import xyz.dcme.library.base.BaseActivity;


public class NodeManagerActivity extends BaseActivity {
    private List<NodeCategory> mData = new ArrayList<>();
    private NodeCategoryAdapter mAdapter;
    private RecyclerView mCategoryRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_node_manage;
    }

    @Override
    public void initView() {
        initNodeData();
        mAdapter = new NodeCategoryAdapter(this, R.layout.item_subscribe, mData);

        mCategoryRecyclerView = (RecyclerView) findViewById(R.id.category_list);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.node_choose);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initNodeData() {
        for (int i : NodeDbHelper.CATEGORY_ARRAY) {
            String category = getString(i);
            List<Node> nodes = NodeDbHelper.getInstance().queryNodesByCategory(this, category);
            NodeCategory cate = new NodeCategory(category, nodes);
            mData.add(cate);
        }
    }
}
