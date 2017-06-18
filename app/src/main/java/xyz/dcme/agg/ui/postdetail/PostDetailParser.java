package xyz.dcme.agg.ui.postdetail;

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
import xyz.dcme.agg.ui.postdetail.data.PostMyComment;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class PostDetailParser {
    private static final String TAG = LogUtils.makeLogTag("PostDetailParser");

    public static List<PostDetailItem> parseResponse(String response) {
        List<PostDetailItem> data = new ArrayList<>();

        Document doc = Jsoup.parse(response);
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
        if (!images.isEmpty()) {
            content = items.get(0).html();
        }
        PostContent postContent = new PostContent(name, avatar, content, createTime, title, clickCount, node);
        data.add(postContent);

        PostMyComment myComment = new PostMyComment(null, null, null, null);
        Elements replyHeaders = doc.select("div.topic-reply div.ui-header span");
        if (replyHeaders != null && !replyHeaders.isEmpty()) {
            String totalCommentCount = replyHeaders.first().text();
            myComment.setTotalCount(totalCommentCount);
        }
        data.add(myComment);

        Elements replyItems = doc.select("div.reply-item");
        for (Element replyItem : replyItems) {
            String replyUserName = replyItem.select("a.reply-username").text();
            String replyContent = replyItem.select("span.content").text();
            String replyAvatar = replyItem.select("img").attr("src");
            String replyTime = replyItem.select("span.time").text();
            data.add(new PostComment(replyUserName, replyAvatar, replyContent, replyTime));
            LogUtils.d(TAG, "name: " + replyUserName + " content: " + replyContent + " avatar: " + replyAvatar);
        }
        return data;
    }

    public static Map<String, String> mockLogin() {
        try {
            final String url = Constants.LOGIN_URL;
            Map<String, String> loginCookies = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                    .execute().cookies();

            String email = "cshenn@163.com";//"1012504657@qq.com";
            String password = "x1234567";//"x1234567X";

            Connection.Response res = Jsoup.connect(url)
                    .data("email", email, "password", password, "_xsrf", loginCookies.get("_xsrf"))
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                    .cookies(loginCookies)
                    .method(Connection.Method.POST).execute();

            return res.cookies();
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
        return new HashMap<String, String>();
    }

}
