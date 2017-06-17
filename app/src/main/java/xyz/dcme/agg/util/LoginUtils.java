package xyz.dcme.agg.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LoginUtils {

    public static boolean needLogin(String response) {
        Document doc = Jsoup.parse(response);
        Elements contents = doc.select("div.ui-content");
        return (contents == null || contents.isEmpty());
    }
}
