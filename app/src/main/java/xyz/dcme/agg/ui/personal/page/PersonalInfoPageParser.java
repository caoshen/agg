package xyz.dcme.agg.ui.personal.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static xyz.dcme.agg.util.LogUtils.LOGE;
import static xyz.dcme.agg.util.LogUtils.makeLogTag;

public class PersonalInfoPageParser {

    private static final String TAG = makeLogTag("PersonalInfoPageParser");

    public Count parse(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOGE(TAG, e.getMessage());
        }

        Count count = new Count();
        if (doc == null) {
            return null;
        }

        Elements topics = doc.select("div.status.status-topic");
        Elements replies = doc.select("div.status.status-reply");
        Elements fav = doc.select("div.status.status-favorite");
        Elements rep = doc.select("div.status.status-reputation");
        return null;
    }
}
