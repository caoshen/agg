package cn.okclouder.ovc.ui.node;

import android.content.Context;

import java.util.List;

import cn.okclouder.ovc.database.NodeLocalData;

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
    public void onItemSwap(List<Node> nodes) {
        NodeLocalData.getInstance(mContext).updateNodes(nodes);
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
