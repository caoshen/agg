package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.List;

import xyz.dcme.agg.database.NodeLocalData;
import xyz.dcme.agg.database.table.CurNodeTable;

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
            List<Node> fixedNodes = NodeLocalData.getInstance(mContext).queryNode(CurNodeTable.CUR_NODE);
            List<Node> moreNodes = NodeLocalData.getInstance(mContext).queryNode(CurNodeTable.MORE_NODE);
            mView.showCurNode(fixedNodes);
            mView.showMoreNode(moreNodes);
        }
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
        NodeLocalData.getInstance(mContext).updateNodes(curNodes, moreNodes);
    }
}
