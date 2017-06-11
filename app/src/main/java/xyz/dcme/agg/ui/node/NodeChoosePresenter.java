package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.List;

import xyz.dcme.agg.database.NodeLocalData;

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
            List<Node> curNodes = NodeLocalData.getInstance(mContext).getCurNode();
            List<Node> moreNodes = NodeLocalData.getInstance(mContext).getMoreNode();
            mView.showCurNode(curNodes);
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
