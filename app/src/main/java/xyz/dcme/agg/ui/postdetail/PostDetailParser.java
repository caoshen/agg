package xyz.dcme.agg.ui.postdetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.model.PostComment;
import xyz.dcme.agg.util.LogUtils;

public class PostDetailParser {
    private static final String TAG = LogUtils.makeLogTag("PostDetailParser");
    private static final String PREFIX = "http://www.guanggoo.com";

    public static List<PostComment> parseComment(String url) {
        if (!url.startsWith(PREFIX)) {
            url = PREFIX + url;
            LogUtils.LOGD(TAG, url);
        }
        Document doc = null;
        List<PostComment> data = new ArrayList<PostComment>();

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Elements items = doc.select("div.ui-content");
        Elements replyItems = doc.select("div.reply-item");
        for (Element replyItem : replyItems) {
            String replyUserName = replyItem.select("a.reply-username").text();
            String replyContent = replyItem.select("span.content").text();
            String replyAvatar = replyItem.select("img.src").text();
            data.add(new PostComment(replyUserName, replyContent, replyAvatar));
            LogUtils.LOGD(TAG, "name: " + replyUserName + " content: " + replyContent + " avatar: " + replyAvatar);
        }
        return data;
    }
}
