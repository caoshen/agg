package xyz.dcme.agg.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.postdetail.PostDetailParser;
import xyz.dcme.agg.util.LogUtils;

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

}
