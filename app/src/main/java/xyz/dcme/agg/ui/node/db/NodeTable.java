package xyz.dcme.agg.ui.node.db;

import android.provider.BaseColumns;

public class NodeTable implements BaseColumns {
    private static final String TABLE_NAME = "nodes";

    private static final String COLUMN_NODE_NAME = "node_name";
    private static final String COLUMN_NODE_TITLE = "node_title";
    private static final String COLUMN_FIXED = "fixed";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_IS_CUR = "current";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + NodeTable.TABLE_NAME + " ( "
            + NodeTable._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
            + NodeTable.COLUMN_NODE_NAME + TEXT_TYPE + COMMA_SEP
            + NodeTable.COLUMN_NODE_TITLE + TEXT_TYPE + COMMA_SEP
            + NodeTable.COLUMN_FIXED + INTEGER_TYPE + COMMA_SEP
            + NodeTable.COLUMN_POSITION + INTEGER_TYPE + COMMA_SEP
            + NodeTable.COLUMN_IS_CUR + INTEGER_TYPE + COMMA_SEP
            + " );";
}
