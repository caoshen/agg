package xyz.dcme.agg.ui.postdetail;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private static final String TAG = LogUtils.makeLogTag("PostDetailPresenter");

    private final PostDetailContract.View mView;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        final String reqUrl = url;
        new AsyncTask<Void, Void, List<PostDetailItem>>() {

            @Override
            protected List<PostDetailItem> doInBackground(Void... voids) {
                return PostDetailParser.parseDetail(reqUrl);
            }

            @Override
            protected void onPostExecute(List<PostDetailItem> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void sendReply(final String comment, final String url) {
        mView.addComment(comment);

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                String tid = extractTidFromUrl(url);
                String content = comment;
                String xsrf = "360b2cac5e274a11bff1b42ef6de9ca5";
                Map<String, String> cookies = PostDetailParser.mockLogin();
                try {
                    Log.d(TAG, tid + " " + content);
                    Connection.Response resp = Jsoup.connect(Constants.WEBSITE_URL + url)
                            .method(Connection.Method.GET)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                            .cookies(cookies)
                            .execute();
                    Map<String, String> reqCookies = resp.cookies();
                    Connection.Response res = Jsoup.connect(Constants.WEBSITE_URL + url)
                            .data("tid", tid, "content", content, "_xsrf", xsrf)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                            .cookies(reqCookies)
                            .method(Connection.Method.POST).execute();
                    Log.d(TAG, "response:\n" + "status code: " + res.statusCode()
                            + "\nstatus message: " + res.statusMessage()
                            + "\nbody: " + res.body());
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (!result) {
                    mView.sendCommentFailed();
                }
            }
        }.execute();
    }

    private String extractTidFromUrl(String url) {
        // For example: /t/20432#reply2
        if (url.startsWith("/t/")) {
            int pos = url.indexOf("#");
            if (pos <= 0) {
                return url.substring(3);
            } else {
                return url.substring(3, pos);
            }
        }
        return "";
    }

    @Override
    public void start() {

    }
}
