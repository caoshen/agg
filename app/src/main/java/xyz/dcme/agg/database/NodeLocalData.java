package xyz.dcme.agg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.node.Node;
import xyz.dcme.agg.util.LogUtils;

public class NodeLocalData {
    private static final String TAG = "NodeLocalData";
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
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = makeContentValues(node);

        String select = NodeTable.COLUMN_NODE_NAME + " = ? ";
        String[] args = new String[]{node.getName()};

        db.update(NodeTable.TABLE_NAME, values, select, args);
    }

    public List<Node> queryNode(int type) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String select = NodeTable.COLUMN_CUR + " = ? ";
        String[] args = new String[]{String.valueOf(type)};

        Cursor cursor = null;
        List<Node> nodes = new ArrayList<>();
        try {
            cursor = db.query(NodeTable.TABLE_NAME, null, select, args, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                Node node = makeNode(cursor);
                nodes.add(node);
            }
        } catch (Exception e) {
            LogUtils.LOGE(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nodes;
    }

    @NonNull
    private Node makeNode(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_NODE_NAME));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_NODE_TITLE));
        int cur = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_CUR));
        int fixed = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_FIXED));
        int pos = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_POSITION));
        Node node = new Node(name, title);
        node.setCurrent(cur);
        node.setFixed(fixed);
        node.setPosition(pos);
        return node;
    }

    public void insertNode(Node node) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

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

    public void initNodeTable(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            List<Node> fixedNodes = getNodes(R.array.fixed_node_name, R.array.fixed_node_title);
            List<Node> allNodes = getNodes(R.array.all_node_name, R.array.all_node_title);

            for (Node node : fixedNodes) {
                node.setFixed(0);
                node.setCurrent(0);
                node.setPosition(fixedNodes.indexOf(node));
                db.insert(NodeTable.TABLE_NAME, null, makeContentValues(node));
            }

            for (Node node : allNodes) {
                node.setFixed(1);
                node.setCurrent(1);
                node.setPosition(allNodes.indexOf(node));
                db.insert(NodeTable.TABLE_NAME, null, makeContentValues(node));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.LOGE(TAG, e.toString());
        } finally {
            db.endTransaction();
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

}
