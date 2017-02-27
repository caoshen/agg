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
            Log.d(TAG, text);
        }
        return data;
    }

    public static List<Post> parseUrl(String url) {
        Document doc = null;
        List<Post> data = new ArrayList<Post>();

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements items = doc.select("div.topic-item");
        for (Element element : items) {
            Element avatar = element.select("img.avatar").first();
            String avatarUrl = avatar.attr("src");
            Element text = element.select("h3.title").first();
            String title = text.text();
            data.add(new Post(title, avatarUrl));
        }
        return data;
    }
}
