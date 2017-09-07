package xyz.dcme.agg.ui.node;

import java.util.List;

public class NodeCategory {
    private String mName;
    private List<Node> mNodeList;

    public NodeCategory(String name, List<Node> nodeList) {
        mName = name;
        mNodeList = nodeList;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Node> getNodeList() {
        return mNodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        mNodeList = nodeList;
    }
}
