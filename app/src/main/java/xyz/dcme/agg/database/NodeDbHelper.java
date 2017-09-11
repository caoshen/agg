package xyz.dcme.agg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.table.NodeTable;
import xyz.dcme.agg.ui.node.Node;
import xyz.dcme.library.util.LogUtils;

public class NodeDbHelper {
    private static final String LOG_TAG = "NodeDbHelper";
    private static final int[] NAME_ARRAY = {R.array.life_nodes_name,
            R.array.social_nodes_name,
            R.array.science_nodes_name,
            R.array.culture_nodes_name,
            R.array.art_nodes_name,
            R.array.leisure_nodes_name,
            R.array.community_nodes_name};
    private static final int[] TITLE_ARRAY = {R.array.life_nodes_title,
            R.array.social_nodes_title,
            R.array.science_nodes_title,
            R.array.culture_nodes_title,
            R.array.art_nodes_title,
            R.array.leisure_nodes_title,
            R.array.community_nodes_title};
    public static final int[] CATEGORY_ARRAY = {R.string.life,
            R.string.social,
            R.string.science,
            R.string.culture,
            R.string.art,
            R.string.leisure,
            R.string.community};
    private static NodeDbHelper mInstance;

    private NodeDbHelper() {
    }

    public static NodeDbHelper getInstance() {
        if (mInstance == null) {
            mInstance = new NodeDbHelper();
        }
        return mInstance;
    }

    private Uri getUri() {
        return NodeTable.CONTENT_URI;
    }

    public void init(Context context, SQLiteDatabase db) {
        for (int i = 0; i < NAME_ARRAY.length; ++i) {
            addNodes(context, db, NAME_ARRAY[i], TITLE_ARRAY[i], CATEGORY_ARRAY[i]);
        }
    }

    private void addNodes(Context context, SQLiteDatabase db, int nodeNameArray, int nodeTitleArray, int category) {
        String[] names = context.getResources().getStringArray(nodeNameArray);
        String[] titles = context.getResources().getStringArray(nodeTitleArray);
        String[] defaultTypes = context.getResources().getStringArray(R.array.default_node_name);
        String cate = context.getString(category);

        if (names.length != titles.length) {
            return;
        }
        for (int i = 0; i < names.length; ++i) {
            int selected = Arrays.asList(defaultTypes).contains(names[i]) ? 1 : 0;
            Node e = new Node(names[i], titles[i], cate, i, selected, 0);
            insertNode(db, e);
        }
    }

    public void insertNode(SQLiteDatabase db, Node node) {
        db.insert(NodeTable.TABLE_NAME, null, makeContentValues(node));
    }

    public List<Node> queryNodesByCategory(Context context, String category) {
        List<Node> nodes = new ArrayList<>();
        String selection = NodeTable.COLUMN_CATEGORY + "=?";
        String[] selectionArgs = new String[]{category};
        String sortOrder = NodeTable.COLUMN_POSITION;

        Cursor cursor = context.getContentResolver().query(getUri(), null, selection, selectionArgs, sortOrder);
        try {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_NAME));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_TITLE));
                    String cate = cursor.getString(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_CATEGORY));
                    int pos = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_POSITION));
                    int selected = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_SELECTED));
                    int defaultType = cursor.getInt(cursor.getColumnIndexOrThrow(NodeTable.COLUMN_FIX));
                    Node n = new Node(name, title, cate, pos, selected, defaultType);
                    nodes.add(n);
                }
            }
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, "queryAllHistory -> exception: " + e);
        }
        if (null != cursor) {
            cursor.close();
        }
        return nodes;
    }

    private ContentValues makeContentValues(Node node) {
        ContentValues cv = new ContentValues();
        cv.put(NodeTable.COLUMN_NAME, node.getName());
        cv.put(NodeTable.COLUMN_TITLE, node.getTitle());
        cv.put(NodeTable.COLUMN_CATEGORY, node.getCategory());
        cv.put(NodeTable.COLUMN_POSITION, node.getPosition());
        cv.put(NodeTable.COLUMN_SELECTED, node.getSelected());
        cv.put(NodeTable.COLUMN_FIX, node.getFix());
        return cv;
    }
}
