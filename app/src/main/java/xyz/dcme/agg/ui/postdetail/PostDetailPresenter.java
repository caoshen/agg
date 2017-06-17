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
    private static final String TAG = LogUtils.makeLogTag("PostDetailPresenter");

    private final PostDetailContract.View mView;
    private Context mContext;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        mContext = mView.getViewContext();
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        mView.showIndicator(true);

        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);

                if (LoginUtils.needLogin(response)) {
                    mView.startLogin();
                } else {
                    mView.onRefresh(PostDetailParser.parseResponse(response));
                }
            }
        });
    }

    @Override
    public void sendReply(final String comment, final String url) {
        mView.showCommentIndicator(true);

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
        params.put("tid", extractTidFromUrl(url));
        params.put("content", comment);
        params.put("_xsrf", HttpUtils.findXsrf(response));

        return params;
    }

    private void reply(final String comment, String url, Map<String, String> params) {
        if (params == null) {
            return;
        }

        HttpUtils.post(getRealUrl(url), params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.sendCommentFailed();
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setCommentSuccess();
                mView.addComment(comment);
            }
        });
    }

    private String getRealUrl(String url) {
        if (url.contains("#")) {
            int pos = url.indexOf("#");
            return Constants.HOME_URL + url.substring(0, pos);
        }
        return Constants.HOME_URL + url;
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
