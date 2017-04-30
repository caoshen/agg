package xyz.dcme.agg.ui.postdetail;

import android.app.Fragment;
import android.content.Context;
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
    private Context mContext;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;

        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        }
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        final String reqUrl = url;
        new AsyncTask<Void, Void, List<PostDetailItem>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showIndicator();
            }

            @Override
            protected List<PostDetailItem> doInBackground(Void... voids) {
                return PostDetailParser.parseDetail(reqUrl);
            }

            @Override
            protected void onPostExecute(List<PostDetailItem> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
                mView.hideIndicator();
            }
        }.execute();
    }

    @Override
    public void sendReply(final String comment, final String url) {

        mView.showCommentIndicator(true);

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                String tid = extractTidFromUrl(url);
                String content = comment;
                Map<String, String> cookies = PostDetailParser.mockLogin();
                try {
                    Log.d(TAG, tid + " " + content);
                    String realUrl = url;

                    if (realUrl.contains("#")) {
                        int pos = realUrl.indexOf("#");
                        realUrl = realUrl.substring(0, pos);
                    }

                    for (String key : cookies.keySet()) {
                        LogUtils.LOGD(TAG, key + ": " + cookies.get(key));
                    }

                    String xsrf = "360b2cac5e274a11bff1b42ef6de9ca5";
                    cookies.put("_xsrf", xsrf);
                    Connection.Response res = Jsoup.connect(Constants.WEBSITE_HOME_URL + realUrl)
                            .data("tid", tid, "content", content, "_xsrf", xsrf)
                            .userAgent(Constants.USER_AGENT)
                            .cookies(cookies)
                            .method(Connection.Method.POST).execute();
                    return res.statusCode() == Constants.HTTP_OK;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (!result) {
                    mView.sendCommentFailed();
                } else {
                    mView.setCommentSuccess();
                    mView.addComment(comment);
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
