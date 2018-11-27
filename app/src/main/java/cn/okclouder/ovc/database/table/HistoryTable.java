package cn.okclouder.ovc.database.table;

import android.net.Uri;
import android.provider.BaseColumns;

import cn.okclouder.ovc.util.Constants;

/*
History table:
|------------------------------------------------|
| author  | avatar  |  title | link |  timestamp |
|---------|---------|--------|------|------------|
*/

public class HistoryTable implements BaseColumns {
    public static final String TABLE_NAME = "history";

    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = " , ";

    public static final String PATH = TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.PROVIDER_AUTH + "/" +PATH);

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ( "
            + _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
            + COLUMN_AUTHOR + TEXT_TYPE + COMMA_SEP
            + COLUMN_AVATAR + TEXT_TYPE + COMMA_SEP
            + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
            + COLUMN_LINK + TEXT_TYPE + COMMA_SEP
            + COLUMN_TIMESTAMP + TEXT_TYPE
            + " );";
}
