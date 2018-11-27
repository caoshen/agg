package cn.okclouder.ovc.ui.topic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static cn.okclouder.library.util.LogUtils.makeLogTag;

public class TopicParser {
    private static final String TAG = makeLogTag("TopicParser");

    public static List<Topic> parseResponse(String response) {
        List<Topic> topics = new ArrayList<>();
        Document doc = Jsoup.parse(response);

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

        return topics;
    }
}
