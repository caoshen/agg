package xyz.dcme.agg.ui.reply;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.dcme.library.util.LogUtils;

import static xyz.dcme.library.util.LogUtils.makeLogTag;

public class ReplyParser {

    private static final String TAG = makeLogTag("ReplyParser");

    public static List<Reply> parseList(String url) {
        Document doc = null;
        List<Reply> replies = new ArrayList<>();

        try {
            doc = Jsoup.connect(url).get();

            if (doc == null) {
                return replies;
            }

            Elements elements = doc.select("div.reply-item");
            for (Element e : elements) {
                String title = e.select("span.title").text();
                String content = e.select("div.content").text();
                String detailUrl = e.select("span.title a").attr("href");

                Reply r = new Reply();
                r.title = title;
                r.content = content;
                r.detailUrl = detailUrl;
                replies.add(r);
            }
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

        return replies;
    }

    public static List<Reply> parseResponse(String response) {
        Document doc;
        List<Reply> replies = new ArrayList<>();

        try {
            doc = Jsoup.parse(response);

            if (doc == null) {
                return replies;
            }

            Elements elements = doc.select("div.reply-item");
            for (Element e : elements) {
                String title = e.select("span.title").text();
                String content = e.select("div.content").text();
                String detailUrl = e.select("span.title a").attr("href");

                Reply r = new Reply();
                r.title = title;
                r.content = content;
                r.detailUrl = detailUrl;
                replies.add(r);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }

        return replies;
    }
}
