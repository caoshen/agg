package xyz.dcme.agg.ui.login;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class AccountParser {

    private static final String TAG = LogUtils.makeLogTag("AccountParser");

    public static AccountInfo parseAccount(String url, Map<String, String> userCookies) {
        if (!url.startsWith(Constants.WEBSITE_URL)) {
            url = Constants.WEBSITE_URL + url;
        }
        AccountInfo accountInfo = new AccountInfo();
        Document doc = null;

        try {
            doc = Jsoup.connect(url).cookies(userCookies).get();
            Element ele = doc.select("div.col-md-3.sidebar-right.mt10").first();
            String link = ele.select("div a").attr("href");
            String avatarUrl = ele.select("div a img").attr("src");
            String id = "";
            if (link.startsWith("/u/")) {
                id = link.substring(3);
            }
            String userName = ele.select("div.username").text();

            accountInfo.setId(id);
            accountInfo.setAvatarUrl(avatarUrl);
            accountInfo.setLink(link);

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }

        return accountInfo;
    }
}
