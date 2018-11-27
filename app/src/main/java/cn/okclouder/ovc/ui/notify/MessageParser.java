package cn.okclouder.ovc.ui.notify;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;

public class MessageParser {
    private static final String LOG_TAG = "MessageParser";

    public static List<Message> parseResponse(String response) {
        List<Message> data = new ArrayList<>();
        Document doc = Jsoup.parse(response);

        Elements items = doc.select("div.notification-item");
        for (Element element : items) {
            Element text = element.select("span.title").first();
            String title = text.text();
            Elements links = text.select("a");
            if (links == null || links.size() < 2) {
                continue;
            }
            String link = links.get(1).attr("href");
            Element contentEle = element.select("div.content").first();
            String content = contentEle.text();
            Message msg = new Message(title, content, link);
            data.add(msg);
        }
        return data;
    }

    public static int parseTotalCount(String response) {
        Document doc = Jsoup.parse(response);

        try {
            Elements items = doc.select("ul.pagination a");
            if (items != null && !items.isEmpty()) {
                int size = items.size();
                if (size < 2) {
                    return 0;
                }
                String counts = items.get(size - 2).text();
                LogUtils.d(LOG_TAG, "parseTotalCount -> counts: " + counts);
                return Integer.valueOf(counts);
            }
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, e.toString());
        }
        return 0;
    }
}
