package cn.okclouder.ovc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.database.table.CurNodeTable;
import cn.okclouder.ovc.database.table.MoreNodeTable;
import cn.okclouder.ovc.ui.node.Node;

public class NodeLocalData {
    private static final String TAG = "NodeLocalData";
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
        deleteNodes(CurNodeTable.TABLE_NAME);
        insertNodes(CurNodeTable.TABLE_NAME, curNodes);
        deleteNodes(MoreNodeTable.TABLE_NAME);
        insertNodes(MoreNodeTable.TABLE_NAME, moreNodes);
    }

    private void insertNodes(String tableName, List<Node> nodes) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            for (Node node : nodes) {
                db.insert(tableName, null, makeContentValues(node));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            db.endTransaction();
        }
    }

    private void deleteNodes(String tableName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(tableName, null, null);
    }

    private ContentValues makeContentValues(Node node) {
        ContentValues values = new ContentValues();
        values.put(CurNodeTable.COLUMN_NODE_NAME, node.getName());
        values.put(CurNodeTable.COLUMN_NODE_TITLE, node.getTitle());
        return values;
    }

    public List<Node> getCurNode() {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        Cursor cursor = null;
        List<Node> nodes = new ArrayList<>();
        try {
            cursor = db.query(CurNodeTable.TABLE_NAME, null, null, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CurNodeTable.COLUMN_NODE_NAME));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(CurNodeTable.COLUMN_NODE_TITLE));
                Node node = new Node(name, title);
                nodes.add(node);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nodes;
    }


    public List<Node> getMoreNode() {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        Cursor cursor = null;
        List<Node> nodes = new ArrayList<>();
        try {
            cursor = db.query(MoreNodeTable.TABLE_NAME, null, null, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MoreNodeTable.COLUMN_NODE_NAME));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MoreNodeTable.COLUMN_NODE_TITLE));
                Node node = new Node(name, title);
                nodes.add(node);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nodes;
    }

    public void updateNodes(List<Node> nodes) {
        deleteNodes(CurNodeTable.TABLE_NAME);
        insertNodes(CurNodeTable.TABLE_NAME, nodes);
    }
}
