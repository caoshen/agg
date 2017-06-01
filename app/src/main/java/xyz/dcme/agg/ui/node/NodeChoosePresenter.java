package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;

public class NodeChoosePresenter implements NodeChooseContract.Presenter {

    private NodeChooseContract.View mView;
    private Context mContext;

    public NodeChoosePresenter(NodeChooseContract.View v) {
        mView = v;
        mView.setPresenter(this);
        mContext = mView.getViewContext();
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        if (mContext != null) {
            List<Node> fixedNodes = getFixedNodes(R.array.fixed_node_name, R.array.fixed_node_title);
            List<Node> AllNodes = getFixedNodes(R.array.all_node_name, R.array.all_node_title);
            mView.showCurNode(fixedNodes);
            mView.showMoreNode(AllNodes);
        }
    }

    private List<Node> getFixedNodes(int nodeNameArray, int nodeTitleArray) {
        String[] names = mContext.getResources().getStringArray(nodeNameArray);
        String[] titles = mContext.getResources().getStringArray(nodeTitleArray);
        List<Node> nodes = new ArrayList<>();

        if (names.length != titles.length) {
            return nodes;
        }
        for (int i = 0; i < names.length; ++i) {
            nodes.add(new Node(names[i] , titles[i]));
        }
        return nodes;
    }

    @Override
    public void onItemSwap() {

    }

    @Override
    public void onItemAdd() {

    }

    @Override
    public void onItemRemove() {
    }

    @Override
    public void onItemChange(List<Node> curNodes, List<Node> moreNodes) {

    }
}
