package cn.okclouder.ovc.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.database.HistoryInfo;
import cn.okclouder.ovc.database.table.HistoryTable;

public class HistoryDbHelper {
    private static final String LOG_TAG = "HistoryDbHelper";
    private static final String TAG = HistoryDbHelper.class.getSimpleName();
    private static HistoryDbHelper mInstance;

    private HistoryDbHelper() {

    }

    public static HistoryDbHelper getInstance() {
        if (null == mInstance) {
            synchronized (HistoryDbHelper.class) {
                if (null == mInstance) {
                    mInstance = new HistoryDbHelper();
                }
            }
        }
        return mInstance;
    }

    public void insertHistory(Context context, HistoryInfo historyInfo) {
        Uri uri = context.getContentResolver().insert(getUri(), makeContentValues(historyInfo));
        LogUtils.d(LOG_TAG, "insertHistory -> " + uri);
    }

    public void deleteAllHistory(Context context) {
        int count = context.getContentResolver().delete(getUri(), null, null);
        LogUtils.d(LOG_TAG, "deleteAllHistory -> delete " + count);
    }

    public List<HistoryInfo> queryAllHistory(Context context) {
        List<HistoryInfo> infoList = new ArrayList<>();
        String sortOrder = HistoryTable.COLUMN_TIMESTAMP + " DESC";
        Cursor cursor = context.getContentResolver().query(getUri(), null, null, null, sortOrder);
        try {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_AUTHOR));
                    String avatar = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_AVATAR));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_TITLE));
                    String link = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_LINK));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_TIMESTAMP));
                    HistoryInfo info = new HistoryInfo(author, avatar, title, link, timestamp);
                    infoList.add(info);
                }
            }
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, "queryAllHistory -> exception: " + e);
        }
        if (null != cursor) {
            cursor.close();
        }
        return infoList;
    }

    public List<HistoryInfo> queryHistoryLimit(Context context, int limit) {
        List<HistoryInfo> infoList = new ArrayList<>();
        String sortOrder = HistoryTable.COLUMN_TIMESTAMP + " DESC " + " LIMIT " + limit;
        Cursor cursor = context.getContentResolver().query(getUri(), null, null, null,
                sortOrder);
        try {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_AUTHOR));
                    String avatar = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_AVATAR));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_TITLE));
                    String link = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_LINK));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(HistoryTable.COLUMN_TIMESTAMP));
                    HistoryInfo info = new HistoryInfo(author, avatar, title, link, timestamp);
                    infoList.add(info);
                }
            }
            if (!infoList.isEmpty()) {
                HistoryInfo historyInfo = infoList.get(infoList.size() - 1);
                String timestamp = historyInfo.timestamp;
                deleteHistoryLimit(context, timestamp);
            }
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, "queryAllHistory -> exception: " + e);
        }
        if (null != cursor) {
            cursor.close();
        }
        return infoList;
    }

    private int deleteHistoryLimit(Context context, String timeStampLimit) {
        String where = HistoryTable.COLUMN_TIMESTAMP + " < " + timeStampLimit;
        int count = context.getContentResolver().delete(getUri(), where, null);
        LogUtils.d(TAG, "deleteHistoryLimit count:" + count);
        return count;
    }

    private ContentValues makeContentValues(HistoryInfo info) {
        ContentValues cv = new ContentValues();
        cv.put(HistoryTable.COLUMN_AUTHOR, info.author);
        cv.put(HistoryTable.COLUMN_AVATAR, info.avatar);
        cv.put(HistoryTable.COLUMN_TITLE, info.title);
        cv.put(HistoryTable.COLUMN_LINK, info.link);
        cv.put(HistoryTable.COLUMN_TIMESTAMP, info.timestamp);
        return cv;
    }

    private Uri getUri() {
        return HistoryTable.CONTENT_URI;
    }
}
