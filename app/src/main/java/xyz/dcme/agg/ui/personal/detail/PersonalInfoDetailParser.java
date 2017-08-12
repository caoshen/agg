package xyz.dcme.agg.ui.personal.detail;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static xyz.dcme.library.util.LogUtils.makeLogTag;

public class PersonalInfoDetailParser {
    private static final String TAG = makeLogTag("PersonalInfoDetailParser");

    public static List<Detail> parseResponse(String response) {
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
