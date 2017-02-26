package xyz.dcme.agg.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostParser {
    private static final String TAG = "PostParser";

    public static List<String> parse(String url) {
        Document doc = null;
        List<String> data = new ArrayList<String>();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.select("h3.title");
        for (Element element : newsHeadlines) {
            String text = element.text();
            data.add(text);
            Log.d(TAG, text);
        }
        return data;
    }
}
