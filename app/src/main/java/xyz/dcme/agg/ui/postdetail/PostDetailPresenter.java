package xyz.dcme.agg.ui.postdetail;

import android.content.Context;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;
import xyz.dcme.agg.util.LoginUtils;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    public static final String LOG_TAG = "PostDetailPresenter";
    private static final String TAG = LogUtils.makeLogTag("PostDetailPresenter");

    private final PostDetailContract.View mView;
    private Context mContext;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        mContext = mView.getViewContext();
        view.setPresenter(this);
    }

    @Override
    public void start(String url) {
        mView.showIndicator(true);

        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                if (LoginUtils.needLogin(response)) {
                    mView.startLogin();
                } else {
                    mView.showRefresh(PostDetailParser.parseResponse(response));
                }
                mView.showIndicator(false);
            }
        });
    }

    @Override
    public void load(String url, final int page) {
        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }
        String realUrl = url;
        if (url.contains("#")) {
            realUrl = url.split("#")[0];
        }

        final String finalUrl = realUrl;
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                int total = PostDetailParser.parseTotalCount(response);
                int nextPage = total - (page - 1);

                if (nextPage <= 0) {
                    LogUtils.d(LOG_TAG, "load -> total page: " + total + " next page: " + nextPage);
                    return;
                }
                String nextUrl = finalUrl +  "?p=" + nextPage;
                LogUtils.d(LOG_TAG, "load -> nextUrl: " + nextUrl);
                load(nextUrl);
            }
        });
    }

    @Override
    public void load(String url) {
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.onLoadMore(PostDetailParser.parseComments(response));
            }
        });
    }

    @Override
    public void refresh(String url) {
        mView.showRefreshingIndicator(true);

        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showRefreshingIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showRefreshingIndicator(false);
                mView.showRefresh(PostDetailParser.parseResponse(response));
            }
        });
    }

    @Override
    public void sendReply(final String comment, final String url) {
        mView.showCommentIndicator(true);

        if (mContext == null) {
            LogUtils.e(TAG, "sendReply -> context is null");
            mView.showCommentIndicator(false);
            return;
        }
        if (!AccountUtils.hasActiveAccount(mContext)) {
            mView.startLogin();
            return;
        }

        startReply(comment, url);
    }

    private void startReply(final String comment, final String url) {
        final String realUrl = getRealUrl(url);
        HttpUtils.get(realUrl, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.sendCommentFailed();
            }

            @Override
            public void onResponse(String response, int id) {
                Map<String, String> params = getReplyParams(response, comment, url);
                reply(comment, realUrl, params);
            }
        });
    }

    private Map<String, String> getReplyParams(String response, String comment, String url) {
        Map<String, String> params = new HashMap<>();
        String tid = extractTidFromUrl(url);
        String xsrf = HttpUtils.findXsrf(response);

        params.put("tid", tid);
        params.put("content", comment);
        params.put("_xsrf", xsrf);

        LogUtils.d(TAG, "getReplyParams -> tid: " + tid + " content: " + comment + " xsrf: " + xsrf);
        return params;
    }

    private void reply(final String comment, String url, Map<String, String> params) {
        if (params == null) {
            return;
        }

        HttpUtils.post(url, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, e.toString());
                mView.sendCommentFailed();
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setCommentSuccess();
            }
        });
    }

    private String getRealUrl(String url) {
        String realUrl = url;
        if (realUrl.contains("#")) {
            int pos = realUrl.indexOf("#");
            realUrl = Constants.HOME_URL + realUrl.substring(0, pos);
        }
        LogUtils.d(TAG, "getRealUrl -> " + realUrl);
        return realUrl;
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
