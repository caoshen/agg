package cn.okclouder.ovc.util;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.okclouder.library.util.LogUtils;

public class MyWebViewClient extends WebViewClient {
    private static final String LOG_TAG = "MyWebViewClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null && url.contains(Constants.HOME_URL + "/u/")) {
            LogUtils.d(LOG_TAG, url);
            Pattern pattern =  Pattern.compile("/u/([a-zA-Z0-9_]+)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String name = matcher.group(1);
                Intent intent = new Intent(Constants.ACTION_USER_PAGE);
                intent.putExtra(Constants.EXTRA_ACCOUNT_NAME, name);
                Context context = view.getContext();
                context.startActivity(intent);
            }
        }
        return true;
    }
}
