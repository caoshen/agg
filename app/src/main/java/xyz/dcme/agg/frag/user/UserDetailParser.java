package xyz.dcme.agg.frag.user;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.ui.personal.detail.Detail;
import xyz.dcme.agg.ui.personal.page.Count;

public class UserDetailParser {

    public static UserDetailInfo parse(String response) {
        String imageUrl = parseImageUrl(response);
        Count count = parseCount(response);
        List<Detail> details = parseDetails(response);
        return new UserDetailInfo(imageUrl, count, details);
    }

    public static String parseImageUrl(String response) {
        Document doc = Jsoup.parse(response);

        Elements dls = doc.select("div.profile div.ui-header img");
        if (dls != null && dls.size() > 0) {
            Element img = dls.first();
            String imgUrl = img.attr("src");
            return imgUrl;
        }
        return null;
    }

    public static Count parseCount(String response) {
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

    public static List<Detail> parseDetails(String response) {
        List<Detail> detailList = new ArrayList<>();
        Document doc = Jsoup.parse(response);

        Elements dls = doc.select("div.profile div.ui-content dl");
        for (Element dl : dls) {
            String key = dl.select("dt").text();
            String value = dl.select("dd").text();
            Detail d = new Detail();
            d.title = key;
            d.content = value;
            detailList.add(d);
        }

        String introTitle = doc.select("div.self-introduction span.title").text();
        String introContent = doc.select("div.self-introduction div.ui-content").text();
        if (!TextUtils.isEmpty(introTitle) && !TextUtils.isEmpty(introContent)) {
            Detail d = new Detail();
            d.title = introTitle;
            d.content = introContent;
            detailList.add(d);
        }

        Element number = doc.select("div.profile div.user-number div.number").first();
        Element since = doc.select("div.profile div.user-number div.since").first();
        if (number == null || since == null) {
            return detailList;
        }

        if (!TextUtils.isEmpty(number.text()) && !TextUtils.isEmpty(since.text())) {
            Detail detailNumber = new Detail();
            detailNumber.title = since.text();
            detailNumber.content = number.text();
            detailList.add(detailNumber);
        }

        return detailList;
    }
}
