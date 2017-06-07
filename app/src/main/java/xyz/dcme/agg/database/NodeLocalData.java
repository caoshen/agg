package xyz.dcme.agg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.node.Node;

public class NodeLocalData {
    private static NodeLocalData mInstance;
    private Context mContext;
    private DBHelper mHelper;

    private NodeLocalData(Context context) {
        mHelper = new DBHelper(context);
        mContext = context;
    }

    public static NodeLocalData getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NodeLocalData.class) {
                if (mInstance == null) {
                    mInstance = new NodeLocalData(context);
                }
            }
        }
        return mInstance;
    }

    public void updateNodes(List<Node> curNodes, List<Node> moreNodes) {
        // TODO
    }

    public void updateNode(Node node) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values = makeContentValues(node);

        String select = NodeTable.COLUMN_NODE_NAME + " = ? ";
        String[] args = new String[]{node.getName()};

        db.update(NodeTable.TABLE_NAME, values, select, args);
    }

    public void insertNode(Node node) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values = makeContentValues(node);

        db.insert(NodeTable.TABLE_NAME, null, values);
    }

    @NonNull
    private ContentValues makeContentValues(Node node) {
        ContentValues values = new ContentValues();
        values.put(NodeTable.COLUMN_NODE_NAME, node.getName());
        values.put(NodeTable.COLUMN_NODE_TITLE, node.getTitle());
        values.put(NodeTable.COLUMN_CUR, node.getCurrent());
        values.put(NodeTable.COLUMN_FIXED, node.getFixed());
        values.put(NodeTable.COLUMN_POSITION, node.getPosition());
        return values;
    }

    public void initNodeTable() {
        List<Node> fixedNodes = getNodes(R.array.fixed_node_name, R.array.fixed_node_title);
        List<Node> allNodes = getNodes(R.array.all_node_name, R.array.all_node_title);

        for (Node node : fixedNodes) {
            node.setFixed(0);
            node.setCurrent(0);
            node.setPosition(fixedNodes.indexOf(node));
            insertNode(node);
        }

        for (Node node : allNodes) {
            node.setFixed(1);
            node.setCurrent(1);
            node.setPosition(allNodes.indexOf(node));
            insertNode(node);
        }
    }

    private List<Node> getNodes(int nodeNameArray, int nodeTitleArray) {
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

    private List<Node> getNodes(int type) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // TODO
        return null;
    }
}
