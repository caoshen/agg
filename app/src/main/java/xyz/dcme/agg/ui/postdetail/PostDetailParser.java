package xyz.dcme.agg.ui.postdetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.select("h3.title").first().text();
        String avatar = doc.select("div.ui-header img").attr("src");
        String name = doc.select("span.username a").text();
        String content = "";
        Elements items = doc.select("div.ui-content");
        for (Element item : items) {
            content = item.html();
            break;
        }
        PostContent postContent = new PostContent(name, avatar, content, title);
        data.add(postContent);

        Elements replyItems = doc.select("div.reply-item");
        for (Element replyItem : replyItems) {
            String replyUserName = replyItem.select("a.reply-username").text();
            String replyContent = replyItem.select("span.content").text();
            String replyAvatar = replyItem.select("img").attr("src");
            data.add(new PostComment(replyUserName, replyAvatar, replyContent));
            LogUtils.LOGD(TAG, "name: " + replyUserName + " content: " + replyContent + " avatar: " + replyAvatar);
        }
        return data;
    }
}
