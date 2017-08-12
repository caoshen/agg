package xyz.dcme.agg.ui.personal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xyz.dcme.library.util.LogUtils;

public class PersonalInfoParser {
    private static final String LOG_TAG = "PersonalInfoParser";

    public static String parseResponse(String response) {
        Document doc = Jsoup.parse(response);

        Elements dls = doc.select("div.profile div.ui-header img");
        if (dls != null && dls.size() > 0) {
            Element img = dls.first();
            String imgUrl = img.attr("src");
            LogUtils.d(LOG_TAG, "parseResponse -> " + imgUrl);
            return imgUrl;
        }
        return null;
    }
}
