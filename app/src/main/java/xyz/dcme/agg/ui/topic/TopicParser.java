package xyz.dcme.agg.ui.topic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static xyz.dcme.agg.util.LogUtils.LOGE;
import static xyz.dcme.agg.util.LogUtils.makeLogTag;

class TopicParser {

    private static final String TAG = makeLogTag("TopicParser");

    public static List<Topic> parseList(String url) {
        Document doc = null;
        List<Topic> topics = new ArrayList<>();

        try {
            doc = Jsoup.connect(url).get();

            if (doc == null) {
                return topics;
            }

            Elements elements = doc.select("div.topic-item");
            for (Element e : elements) {
                String title = e.select("h3.title a").text();
                String lastTouched = e.select("span.last-touched").text();
                String replyCount = e.select("div.count a").text();
                String name = e.select("span.username a").text();
                String avatar = e.select("img").attr("src");
                String detailUrl = e.select("h3.title a").attr("href");

                Topic d = new Topic();
                d.authorName = name;
                d.authorAvatar = avatar;
                d.title = title;
                d.lastTouched = lastTouched;
                d.replyCount = replyCount;
                d.detailUrl = detailUrl;
                topics.add(d);
            }
        } catch (IOException e) {
            LOGE(TAG, e.getMessage());
        }

        return topics;
    }
}
