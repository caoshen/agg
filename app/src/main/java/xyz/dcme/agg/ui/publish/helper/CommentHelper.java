package xyz.dcme.agg.ui.publish.helper;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;

public class CommentHelper {
    private static final String LOG_TAG = "CommentHelper";

    public void sendComment(final String comment, final String url, final StringCallback callback) {
        final String realUrl = getRealUrl(url);
        HttpUtils.get(realUrl, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "sendComment -> " + e);
                callback.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                Map<String, String> params = getCommentParams(response, comment, realUrl);
                postComment(realUrl, params, callback);
            }
        });
    }

    private void postComment(String url, Map<String, String> params, final StringCallback callback) {
        if (params == null) {
            return;
        }

        HttpUtils.post(url, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "postComment -> " + e);
                callback.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                callback.onResponse(response, id);
            }
        });
    }

    private Map<String, String> getCommentParams(String response, String comment, String url) {
        Map<String, String> params = new HashMap<>();
        String tid = extractTidFromUrl(url);
        String xsrf = HttpUtils.findXsrf(response);

        params.put("tid", tid);
        params.put("content", comment);
        params.put("_xsrf", xsrf);

        LogUtils.d(LOG_TAG, "getCommentParams -> tid: " + tid + " content: " + comment + " xsrf: " + xsrf);
        return params;
    }

    /**
     * @param url www.guanggoo.com/t/123#reply1
     * @return post tid
     */
    private String extractTidFromUrl(String url) {
        if (url.startsWith(Constants.HOME_URL)) {
            String[] splits = url.split(Constants.HOME_URL);
            if (splits.length == 2) {
                url = splits[1];
            }
        }
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

    private String getRealUrl(String url) {
        String realUrl = url;
        if (!realUrl.startsWith(Constants.HOME_URL)) {
            realUrl = Constants.HOME_URL + realUrl;
        }
        if (realUrl.contains("#")) {
            int pos = realUrl.indexOf("#");
            realUrl = realUrl.substring(0, pos);
        }
        LogUtils.d(LOG_TAG, "getRealUrl -> " + realUrl);
        return realUrl;
    }
}
