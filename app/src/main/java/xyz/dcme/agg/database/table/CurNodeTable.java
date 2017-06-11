package xyz.dcme.agg.database.table;

import android.provider.BaseColumns;

public class CurNodeTable implements BaseColumns {
    public static final String TABLE_NAME = "cur_nodes";

    public static final String COLUMN_NODE_NAME = "node_name";
    public static final String COLUMN_NODE_TITLE = "node_title";
    public static final String COLUMN_FIXED = "fixed";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ( "
            + _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
            + COLUMN_NODE_NAME + TEXT_TYPE + COMMA_SEP
            + COLUMN_NODE_TITLE + TEXT_TYPE + COMMA_SEP
            + COLUMN_FIXED + INTEGER_TYPE
            + " );";
}
