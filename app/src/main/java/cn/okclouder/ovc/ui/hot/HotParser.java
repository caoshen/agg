package cn.okclouder.ovc.ui.hot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.model.Post;

public class HotParser {
    private static final String TAG = "HotParser";

    public static List<Post> parseResponse(String response) {
        Document doc = Jsoup.parse(response);
        List<Post> data = new ArrayList<>();

        Elements items = doc.select("div.cell");
        if (null == items || items.isEmpty()) {
            LogUtils.e(TAG, "parseUrl -> " + doc.text());
            return data;
        }

        for (Element element : items) {
            Element avatar = element.select("img.avatar").first();
            String avatarUrl = avatar.attr("src");
            Element text = element.select("span.hot_topic_title").first();
            String title = text.text();
            String link = text.select("a").first().attr("href");
            Element user = element.select("td a").first();
            String userUrl = user.attr("href");
            String userName = null;
            if (userUrl.lastIndexOf("/") > -1) {
                int i = userUrl.lastIndexOf("/");
                userName = userUrl.substring(i + 1);
            }
            data.add(new Post(title, avatarUrl, link, userName, null, null, null));
        }
        return data;
    }
}
