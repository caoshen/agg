package xyz.dcme.agg.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class HtmlUtils {
    public static String makeHtml(String body) {
        String html = "<!DOCTYPE html>"
                + "<html lang=\"zh-CN\">"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "<meta id = \"viewport\" name = \"viewport\"\n" +
                " content = \"width=device-width, initial-scale=1, minimum-scale=1, user-scalable=no\" >"
                + "<link href=\"file:///android_asset/agg_detail_style.css\" rel=\"stylesheet\">"
                + "</head>"
                + "<body>"
                + body
                + "</body>"
                + "</html>";
        return html;
    }

    public static void initWebSettings(WebView webView) {
        WebSettings ws = webView.getSettings();

        ws.setJavaScriptEnabled(true);

        ws.setAllowFileAccess(true);

        ws.setDatabaseEnabled(true);

        ws.setDomStorageEnabled(true);

        ws.setSaveFormData(false);

        ws.setAppCacheEnabled(true);

        ws.setCacheMode(WebSettings.LOAD_DEFAULT);

        ws.setLoadWithOverviewMode(false);//<==== 一定要设置为false，不然有声音没图像

        ws.setUseWideViewPort(true);
    }

    public static String transformHtml(String html) {
        html = StringUtils.replace(html, "&", "&amp;");
        html = StringUtils.replace(html, "<", "&lt;");
        html = StringUtils.replace(html, ">", "&gt;");
        return html;
    }

    public static String stripHtml(String str) {
        String content = str.replaceAll("</?[^>]+>", "");
        content = content.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return content;
    }

    public static String replaceStaticEmoji(String str) {
        return str.replaceAll("src=\"/static/emoji/", "src=\"" + Constants.HOME_URL + "/static/emoji/");
    }
}
