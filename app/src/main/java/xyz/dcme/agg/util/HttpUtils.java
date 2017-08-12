package xyz.dcme.agg.util;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Map;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import xyz.dcme.library.util.LogUtils;

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    private static final String EMPTY_STR = "";

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

    public static void post(String url, String fileName, File file, StringCallback callback) {
        OkHttpUtils.post()
                .addFile("files", fileName, file)
                .url(url)
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
        Elements select = doc.select("input[type=hidden][name=_xsrf]");
        if (select.isEmpty()) {
            LogUtils.e(TAG, "findXsrf -> response: " + response);
            return EMPTY_STR;
        }
        Element element = select.get(0);
        return element.attr("value");
    }
}
