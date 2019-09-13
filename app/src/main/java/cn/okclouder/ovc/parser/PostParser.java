package cn.okclouder.ovc.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.model.Post;

public class PostParser {
    private static final String TAG = "PostParser";

    public static List<Post> parseResponse(String response) {
        Document doc = Jsoup.parse(response);
        List<Post> data = new ArrayList<>();

        Elements items = doc.select("div.topic-item");
        if (null == items || items.isEmpty()) {
            LogUtils.e(TAG, "parseUrl -> " + doc.text());
            return data;
        }

        for (Element element : items) {
            Element avatar = element.select("img.avatar").first();
            String avatarUrl = avatar.attr("src");
            Element text = element.select("h3.title").first();
            String title = text.text();
            String link = text.select("a").first().attr("href");
            String userName = element.select("span.username").text();
            String lastVisitTime = element.select("span.last-touched").text();
            String node = element.select("span.node").text();
            String commentCount = element.select("div.count").text();
            data.add(new Post(title, avatarUrl, link, userName, lastVisitTime, node, commentCount));
        }
        return data;
    }

    public static int parseTotalCount(String response) {
        Document doc = Jsoup.parse(response);

        try {
            Elements items = doc.select("div.pagination-wap div");
            if (items != null && !items.isEmpty()) {
                String counts = items.first().text();
                LogUtils.d(TAG, "parseTotalCount -> counts: " + counts);
                if (counts.contains("/")) {
                    String[] split = counts.split("/");
                    if (split.length == 2) {
                        return Integer.valueOf(split[1]);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return 0;
    }
}
