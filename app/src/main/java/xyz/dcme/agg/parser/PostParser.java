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

    public static List<Post> parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        List<Post> data = new ArrayList<Post>();

        Elements items = doc.select("div.topic-item");
        if (null == items || items.isEmpty()) {
            Log.e(TAG, "parseUrl -> " + doc.text());
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

    public static List<Post> parseUrl(String url) {
        Document doc = null;
        List<Post> data = new ArrayList<Post>();

        Elements items = null;
        try {
            doc = Jsoup.connect(url).get();

            items = doc.select("div.topic-item");
            if (null == items || items.isEmpty()) {
                Log.e(TAG, "parseUrl -> " + doc.text());
                Map<String, String> userCookies = PostDetailParser.mockLogin();
                doc = Jsoup.connect(url).cookies(userCookies).get();
                items = doc.select("div.topic-item");
                Log.d(TAG, doc.text());
            }
        } catch (IOException e) {
            Log.e(TAG, "parseUrl -> " + e);
        }

        if (null == items || items.isEmpty()) {
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
