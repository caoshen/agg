package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.database.NodeLocalData;
import xyz.dcme.agg.database.NodeTable;

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
            List<Node> fixedNodes = NodeLocalData.getInstance(mContext).queryNode(NodeTable.CUR_NODE);
            List<Node> moreNodes = NodeLocalData.getInstance(mContext).queryNode(NodeTable.MORE_NODE);
            mView.showCurNode(fixedNodes);
            mView.showMoreNode(moreNodes);
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
