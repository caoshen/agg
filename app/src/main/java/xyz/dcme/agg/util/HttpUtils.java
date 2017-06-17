package xyz.dcme.agg.util;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

public class HttpUtils {

    private HttpUtils() {

    }

    public static void get(String url, StringCallback callback) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(callback);
    }

    public  static void get(String url, Map<String, String> headers, StringCallback callback) {
        OkHttpUtils.get()
                .url(url)
                .headers(headers)
                .build()
                .execute(callback);
    }

    public static void post(String url, Map<String, String> params, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(callback);
    }

    public static void post(String url, Map<String, String> headers, Map<String, String> params, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .headers(headers)
                .params(params)
                .build()
                .execute(callback);
    }

    public static void cleanCookie() {
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        CookieJar cookieJar = client.cookieJar();
        if (cookieJar instanceof ClearableCookieJar) {
            ((ClearableCookieJar) cookieJar).clear();
        }
    }

    public static String findXsrf(String response) {
        Document doc = Jsoup.parse(response);
        Elements select = doc.select("input[type=hidden]");
        if (select.isEmpty()) {
            return null;
        }
        Element element = select.get(0);
        return element.attr("value");
    }
}
