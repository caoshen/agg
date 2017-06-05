package xyz.dcme.agg.ui.node.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import xyz.dcme.agg.ui.node.Node;

public class NodeLocalData {
    private static NodeLocalData mInstance;
    private DBHelper mHelper;

    private NodeLocalData(Context context) {
        mHelper = new DBHelper(context);
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

    public void updateDb(Node node) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(NodeTable.COLUMN_NODE_NAME, node.getName());
        values.put(NodeTable.COLUMN_NODE_TITLE, node.getTitle());
        values.put(NodeTable.COLUMN_CUR, node.getCurrent());
        values.put(NodeTable.COLUMN_FIXED, node.getFixed());
        values.put(NodeTable.COLUMN_POSITION, node.getPosition());

        String select = NodeTable.COLUMN_NODE_NAME + " = ? ";
        String[] args = new String[]{node.getName()};

        db.update(NodeTable.TABLE_NAME, values, select, args);
    }
}
