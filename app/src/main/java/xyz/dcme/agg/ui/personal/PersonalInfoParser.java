package xyz.dcme.agg.ui.personal;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import xyz.dcme.agg.parser.IParser;

import static xyz.dcme.agg.util.LogUtils.LOGD;
import static xyz.dcme.agg.util.LogUtils.makeLogTag;

public class PersonalInfoParser implements IParser {
    private static final String TAG = makeLogTag("PersonalInfoParser");


    @Override
    public PersonalInfo parse(String url) {
        Document doc = null;
        PersonalInfo info = new PersonalInfo();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOGD(TAG, e.getMessage());
        }

        if (doc == null) {
            return info;
        }

        Elements dls = doc.select("div.profile div.ui-content dl");
        HashMap<String, String> details = new HashMap<>();
        for (Element dl : dls) {
            String key = dl.select("dt").text();
            String value = dl.select("dd").text();
            details.put(key, value);
        }

        String username = doc.select("div.profile div.username").first().text();
        info.setUserName(username);

        String number = doc.select("div.profile div.number").first().text();
        info.setNumber(number);

        String since = doc.select("div.profile div.since").first().text();
        info.setSince(since);

        String topicsCount = doc.select("div.status.status-topic a").text();
        String repliesCount = doc.select("div.status.status-reply a").text();
        String favouritesCount = doc.select("div.status.status-favorite a").text();
        String reputationCount = doc.select("div.status.status-reputation a").text();
        info.setTopicsCount(topicsCount);
        info.setRepliesCount(repliesCount);
        info.setFavouritesCount(favouritesCount);
        info.setReputationCount(reputationCount);

        String introTitle = doc.select("div.self-introduction span.title").text();
        String introContent = doc.select("div.self-introduction div.ui-content").text();
        if (!TextUtils.isEmpty(introTitle) && !TextUtils.isEmpty(introContent)) {
            details.put(introTitle, introContent);
        }

        info.setDetails(details);

        return info;
    }

    @Override
    public List<PersonalInfo> parseList(String url) {
        return null;
    }
}
