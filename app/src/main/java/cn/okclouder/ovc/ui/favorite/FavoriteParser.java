package cn.okclouder.ovc.ui.favorite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.model.Post;

public class FavoriteParser {
    public static List<Post> parseList(String url) {
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
            String link = text.select("a").first().attr("href");
            String userName = element.select("span.username").text();
            String lastVisitTime = element.select("span.last-touched").text();
            String node = element.select("span.node").text();
            String commentCount = element.select("div.count").text();
            data.add(new Post(title, avatarUrl, link, userName, lastVisitTime, node, commentCount));
        }
        return data;
    }

    public static List<Post> parseResponse(String response) {
        List<Post> data = new ArrayList<>();
        Document doc = Jsoup.parse(response);

        Elements items = doc.select("div.topic-item");
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
