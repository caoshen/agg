package cn.okclouder.ovc.ui.personal.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static cn.okclouder.library.util.LogUtils.makeLogTag;

public class PersonalInfoPageParser {
    private static final String TAG = makeLogTag("PersonalInfoPageParser");

    public static Count parseResponse(String response) {
        Document doc = Jsoup.parse(response);
        Count count = new Count();

        Elements topics = doc.select("div.status.status-topic");
        Elements replies = doc.select("div.status.status-reply");
        Elements fav = doc.select("div.status.status-favorite");
        Elements rep = doc.select("div.status.status-reputation");

        if (topics != null && !topics.isEmpty()) {
            count.topicCount = topics.first().select("a").text();
        }
        if (replies != null && !replies.isEmpty()) {
            count.replyCount = replies.first().select("a").text();
        }
        if (fav != null && !fav.isEmpty()) {
            count.favCount = fav.first().select("a").text();
        }
        if (rep != null && !rep.isEmpty()) {
            count.reputationCount = rep.first().select("strong").text();
        }
        return count;
    }
}
