package xyz.dcme.agg.database.table;

import android.net.Uri;
import android.provider.BaseColumns;

import xyz.dcme.agg.util.Constants;

/*
Node table:
|------------------------------------------------------------|
| name  | title  | category | selected |  position | default |
|-------|--------|----------|----------|-----------|---------|
| city  |城市建设| 生活百科  | 1        | 1         | 1       |
|-------|--------|----------|----------|-----------|---------|
*/

public class NodeTable implements BaseColumns {
    public static final String TABLE_NAME = "cur_nodes";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SELECTED = "selected";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_DEFAULT = "default";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = " , ";

    private static final String PATH = TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.PROVIDER_AUTH + "/" +PATH);

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ( "
            + _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
            + COLUMN_NAME + TEXT_TYPE + COMMA_SEP
            + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
            + COLUMN_CATEGORY + TEXT_TYPE + COMMA_SEP
            + COLUMN_SELECTED + INTEGER_TYPE + COMMA_SEP
            + COLUMN_POSITION + INTEGER_TYPE + COMMA_SEP
            + COLUMN_DEFAULT + INTEGER_TYPE
            + " );";

}
