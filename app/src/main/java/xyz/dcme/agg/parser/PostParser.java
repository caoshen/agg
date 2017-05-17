package xyz.dcme.agg.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.model.Post;

public class PostParser {
    private static final String TAG = "PostParser";

    public static List<String> parse(String url) {
        Document doc = null;
        List<String> data = new ArrayList<String>();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.select("h3.title");
        for (Element element : newsHeadlines) {
            String text = element.text();
            data.add(text);
        }
        return data;
    }

    public static List<Post> parseUrl(String url) {
        Document doc = null;
        List<Post> data = new ArrayList<Post>();

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.e(TAG, "parseUrl -> " + e);
        }
        Elements items = doc.select("div.topic-item");
        if (null == items || items.isEmpty()) {
            Log.e(TAG, "parseUrl -> " + doc.text());
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
