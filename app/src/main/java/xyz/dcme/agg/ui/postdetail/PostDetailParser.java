package xyz.dcme.agg.ui.postdetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.model.PostComment;

public class PostDetailParser {
    private static final String PREFIX = "http://www.guanggoo.com/";

    public static List<PostDetailType> parseUrl(String url) {
        if (!url.startsWith(PREFIX)) {
            url = PREFIX + url;
        }
        Document doc = null;
        List<PostDetailType> data = new ArrayList<PostDetailType>();

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements items = doc.select("div.ui-content");
        String content = items.first().text();
        for (Element element : items) {
            Element avatar = element.select("img.avatar").first();
            String avatarUrl = avatar.attr("src");
            Element text = element.select("h3.title").first();
            String title = text.text();
            data.add(new PostComment(title, avatarUrl));
        }
        return data;
    }
}
