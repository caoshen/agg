package xyz.dcme.agg.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import xyz.dcme.agg.database.table.HistoryTable;
import xyz.dcme.agg.database.table.NodeTable;
import xyz.dcme.agg.util.Constants;


public class ContentProviders extends ContentProvider {

    public static final String ERROR_TABLE_MATCH = "No table matched";
    public static final String MIME_HISTORY = "vnd.android.cursor.dir/" + HistoryTable.TABLE_NAME;
    public static final String MIME_NODE = "vnd.android.cursor.dir/" + NodeTable.TABLE_NAME;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_HISTORY = 1;
    private static final int CODE_NODE = 2;

    static {
        URI_MATCHER.addURI(Constants.PROVIDER_AUTH, HistoryTable.PATH, CODE_HISTORY);
        URI_MATCHER.addURI(Constants.PROVIDER_AUTH, NodeTable.PATH, CODE_NODE);
    }

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    private String getTableName(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_HISTORY: {
                return HistoryTable.TABLE_NAME;
            }
            case CODE_NODE: {
                return NodeTable.TABLE_NAME;
            }
            default: {
                return ERROR_TABLE_MATCH;
            }
        }
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        mDb = mDBHelper.getWritableDatabase();
        return mDb.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_HISTORY: {
                return MIME_HISTORY;
            }
            case CODE_NODE: {
                return MIME_NODE;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        mDb = mDBHelper.getWritableDatabase();
        long insert = mDb.insert(getTableName(uri), null, values);
        notifyChange(uri);
        if (insert > 0) {
            return ContentUris.withAppendedId(uri, insert);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        mDb = mDBHelper.getWritableDatabase();

        int count = mDb.delete(getTableName(uri), selection, selectionArgs);
        notifyChange(uri);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        mDb = mDBHelper.getWritableDatabase();
        int count = mDb.update(getTableName(uri), values, selection, selectionArgs);
        notifyChange(uri);
        return count;
    }

    private void notifyChange(@NonNull Uri uri) {
        if (null != getContext()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
