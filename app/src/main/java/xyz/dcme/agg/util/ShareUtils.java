package xyz.dcme.agg.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import xyz.dcme.agg.R;

public class ShareUtils {

    private ShareUtils() {

    }

    public static void shareText(Context context, String title, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent shareIntent = buildShareIntent(title, url);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));
    }

    private static Intent buildShareIntent(String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = url;
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            text = title + " " + url;
        }
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }
}
