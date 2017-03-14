package xyz.dcme.agg.ui.postdetail;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.dcme.agg.ui.postdetail.data.PostComment;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.util.LogUtils;

public class PostDetailParser {
    private static final String TAG = LogUtils.makeLogTag("PostDetailParser");
    private static final String PREFIX = "http://www.guanggoo.com";

    public static List<PostDetailItem> parseDetail(String url) {
        if (!url.startsWith(PREFIX)) {
            url = PREFIX + url;
            LogUtils.LOGD(TAG, url);
        }
        Document doc = null;
        List<PostDetailItem> data = new ArrayList<PostDetailItem>();

        try {
            doc = Jsoup.connect(url).get();
            Elements contents = doc.select("div.ui-content");
            if (contents == null || contents.isEmpty()) {
                Map<String, String> userCookies = mockLogin();
                doc = Jsoup.connect(url).cookies(userCookies).get();
                LogUtils.LOGD(TAG, doc.body().html());
                Log.d(TAG, doc.body().html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements images = doc.getElementsByTag("img");
        for (Element image : images) {
            image.attr("width", "100%");
            image.attr("height", "auto");
        }

        String title = doc.select("h3.title").first().text();
        String avatar = doc.select("div.ui-header img").attr("src");
        String name = doc.select("span.username a").text();
        String createTime = doc.select("span.created-time").text();
        String node = doc.select("span.node").text();
        String clickCount = doc.select("span.hits.fr.mr10").text();
        String content = "";
        Elements items = doc.select("div.ui-content");
        for (Element item : items) {
            content = item.html();
            break;
        }
        PostContent postContent = new PostContent(name, avatar, content, createTime, title, clickCount, node);
        data.add(postContent);

        Elements replyItems = doc.select("div.reply-item");
        for (Element replyItem : replyItems) {
            String replyUserName = replyItem.select("a.reply-username").text();
            String replyContent = replyItem.select("span.content").text();
            String replyAvatar = replyItem.select("img").attr("src");
            String replyTime = replyItem.select("span.time").text();
            data.add(new PostComment(replyUserName, replyAvatar, replyContent, replyTime));
            LogUtils.LOGD(TAG, "name: " + replyUserName + " content: " + replyContent + " avatar: " + replyAvatar);
        }
        return data;
    }

    public static Map<String, String> mockLogin() {
        try {
            final String url = PREFIX + "/login";
            Map<String, String> loginCookies = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                    .execute().cookies();

            String email = "1012504657@qq.com";
            String password = "x1234567X";

            Connection.Response res = Jsoup.connect(url)
                    .data("email", email, "password", password, "_xsrf", loginCookies.get("_xsrf"))
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                    .cookies(loginCookies)
                    .method(Connection.Method.POST).execute();

            Log.d(TAG, " login response:\n" + "status code: " + res.statusCode()
                    + "\nstatus message: " + res.statusMessage()
                    + "\nbody: " + res.body());
            return res.cookies();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return new HashMap<String, String>();
    }
}
