package xyz.dcme.agg.frag.node;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.agg.database.NodeDbHelper;
import xyz.dcme.agg.ui.node.Node;
import xyz.dcme.agg.ui.node.NodeCategory;
import xyz.dcme.agg.ui.node.NodeCategoryAdapter;


public class NodeAllCategoryFragment extends BaseFragment {
    private List<NodeCategory> mData = new ArrayList<>();

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_node_all_category, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initNodeData();
        NodeCategoryAdapter adapter = new NodeCategoryAdapter(getActivity(), R.layout.item_subscribe, mData);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        QMUITopBar topbar = (QMUITopBar) view.findViewById(R.id.topbar);
        topbar.setTitle(R.string.node_choose);
        topbar.addLeftImageButton(R.drawable.ic_topbar_back_blue, R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
    }

    private void initNodeData() {
        for (int i : NodeDbHelper.CATEGORY_ARRAY) {
            String category = getString(i);
            List<Node> nodes = NodeDbHelper.getInstance().queryNodesByCategory(getActivity(), category);
            NodeCategory cate = new NodeCategory(category, nodes);
            mData.add(cate);
        }
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }
}
