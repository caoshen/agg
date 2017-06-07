package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.database.NodeLocalData;
import xyz.dcme.agg.database.NodeTable;

public class NodeMainPresenter implements NodeMainContract.Presenter {

    private List<Node> mNodes = new ArrayList<>();
    private final NodeMainContract.View mView;
    private Context mContext;

    public NodeMainPresenter(NodeMainContract.View v) {
        mView = v;
        mView.setPresenter(this);
        mContext = mView.getViewContext();
        initNode();
    }

    private void initNode() {
        mNodes = NodeLocalData.getInstance(mContext).queryNode(NodeTable.CUR_NODE);
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        mView.showNodes(mNodes);
    }
}
