package cn.okclouder.ovc.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipBoardUtils {

    private ClipBoardUtils() {

    }

    public static void copyText(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, text));
    }
}
