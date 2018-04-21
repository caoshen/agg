package xyz.dcme.account;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

import xyz.dcme.library.util.LogUtils;

public class AccountParser {

    private static final String TAG = LogUtils.makeLogTag("AccountParser");

    public static AccountInfo parseResponse(String response) {
        AccountInfo accountInfo = new AccountInfo();
        Document doc = Jsoup.parse(response);

        try {
            Element ele = doc.select("div.col-sm-8 div").first();
            String avatarUrl = ele.select("img").first().attr("src");
            String userName = doc.select("input#username").attr("value");

            LogUtils.d(TAG, "username: " + userName + " avatar: " + avatarUrl);

            if (TextUtils.isEmpty(avatarUrl) || TextUtils.isEmpty(userName)) {
                return accountInfo;
            } else {
                accountInfo.setUserName(userName);
                accountInfo.setAvatarUrl(avatarUrl);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }

        return accountInfo;
    }

    public static AccountInfo parseAccount(String url, Map<String, String> userCookies) {
        if (!url.startsWith(LoginConstants.HOME_URL)) {
            url = LoginConstants.HOME_URL + url;
        }
        AccountInfo accountInfo = new AccountInfo();
        Document doc = null;

        try {
            doc = Jsoup.connect(url).cookies(userCookies).get();
            Element ele = doc.select("div.col-sm-8 div").first();
            String avatarUrl = ele.select("img").first().attr("src");
            String userName = doc.select("input#username").attr("value");

            LogUtils.d(TAG, "username: " + userName + " avatar: " + avatarUrl);
            accountInfo.setUserName(userName);
            accountInfo.setAvatarUrl(avatarUrl);

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }

        return accountInfo;
    }
}
