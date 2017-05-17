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
        Node nodeLowShine = new Node("lowshine", "同城活动");
        // Job node needs login first!
        Node nodeJob = new Node("job", "找工作");
        Node nodeIT = new Node("IT", "IT技术");
        mNodes.add(nodeQna);
        mNodes.add(nodeLowShine);
        mNodes.add(nodeJob);
        mNodes.add(nodeIT);
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        mView.showNodes(mNodes);
    }
}
