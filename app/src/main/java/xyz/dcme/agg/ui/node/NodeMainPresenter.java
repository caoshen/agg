package xyz.dcme.agg.ui.node;

import java.util.ArrayList;
import java.util.List;

public class NodeMainPresenter implements NodeMainContract.Presenter {

    private List<Node> mNodes = new ArrayList<>();
    private final NodeMainContract.View mView;

    public NodeMainPresenter(NodeMainContract.View v) {
        mView = v;
        mView.setPresenter(this);

        initNode();
    }

    private void initNode() {
        Node nodeQna = new Node("qna", "你问我答");
        Node nodeJob = new Node("job", "找工作");
        mNodes.add(nodeQna);
        mNodes.add(nodeJob);
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        mView.showNodes(mNodes);
    }
}
