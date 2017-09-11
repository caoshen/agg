package xyz.dcme.agg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.table.CurNodeTable;
import xyz.dcme.agg.database.table.HistoryTable;
import xyz.dcme.agg.database.table.MoreNodeTable;
import xyz.dcme.agg.database.table.NodeTable;
import xyz.dcme.agg.ui.node.Node;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.util.LogUtils;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "settings.db";
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CurNodeTable.CREATE_TABLE);
        db.execSQL(MoreNodeTable.CREATE_TABLE);
        db.execSQL(HistoryTable.CREATE_TABLE);
        db.execSQL(NodeTable.CREATE_TABLE);
//        initTables(db);
        initNodeTable(db);
    }

    private void initTables(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            List<Node> fixedNodes = getNodes(R.array.fixed_node_name, R.array.fixed_node_title);
            List<Node> allNodes = getNodes(R.array.all_node_name, R.array.all_node_title);

            for (Node node : fixedNodes) {
                node.setFixed(Constants.FIXED_NODE);
                db.insert(CurNodeTable.TABLE_NAME, null, makeContentValues(node));
            }

            for (Node node : allNodes) {
                db.insert(MoreNodeTable.TABLE_NAME, null, makeContentValues(node));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            db.endTransaction();
        }
    }

    private void initNodeTable(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            NodeDbHelper.getInstance().init(mContext, db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    private List<Node> getNodes(int nodeNameArray, int nodeTitleArray) {
        String[] names = mContext.getResources().getStringArray(nodeNameArray);
        String[] titles = mContext.getResources().getStringArray(nodeTitleArray);
        List<Node> nodes = new ArrayList<>();

        if (names.length != titles.length) {
            return nodes;
        }
        for (int i = 0; i < names.length; ++i) {
            Node n = new Node(names[i], titles[i]);
            n.setFix(1);
            nodes.add(n);
        }
        return nodes;
    }

    private ContentValues makeContentValues(Node node) {
        ContentValues values = new ContentValues();
        values.put(CurNodeTable.COLUMN_NODE_NAME, node.getName());
        values.put(CurNodeTable.COLUMN_NODE_TITLE, node.getTitle());
        return values;
    }
}
