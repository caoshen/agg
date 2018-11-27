package cn.okclouder.ovc.ui.node;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.database.NodeDbHelper;

public class NodeMainPresenter implements NodeMainContract.Presenter {

    private List<Node> mNodes = new ArrayList<>();
    private final NodeMainContract.View mView;
    private Context mContext;

    public NodeMainPresenter(NodeMainContract.View v) {
        mView = v;
        mView.setPresenter(this);
        mContext = mView.getViewContext();
    }

    private void initNode() {
        mNodes = NodeDbHelper.getInstance().querySelectedNodes(mContext);
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        initNode();
        mView.showNodes(mNodes);
    }
}
