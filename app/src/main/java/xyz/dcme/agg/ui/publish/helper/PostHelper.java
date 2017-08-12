package xyz.dcme.agg.ui.publish.helper;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class PostHelper {
    private static final String LOG_TAG = "PostHelper";

    public void sendPost(final String title, final String content, String node, final StringCallback callback) {
        final String url = getNodeUrl(node);
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "sendPost -> send error: " + e);
                callback.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                Map<String, String> params = getPostParams(response, title, content);
                doPost(url, params, callback);
            }
        });
    }

    private String getNodeUrl(String node) {
        if (!node.startsWith(Constants.CREATE_POST_URL)) {
            return Constants.CREATE_POST_URL + node;
        } else {
            return node;
        }
    }

    private void doPost(String url, Map<String, String> params, final StringCallback callback) {
        if (params == null) {
            return;
        }

        HttpUtils.post(url, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "doPost -> send error: " + e);
                callback.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                callback.onResponse(response, id);
            }
        });
    }

    private Map<String, String> getPostParams(String response, String title, String content) {
        Map<String, String> params = new HashMap<>();
        String xsrf = HttpUtils.findXsrf(response);

        params.put("title", title);
        params.put("content", content);
        params.put("_xsrf", xsrf);

        LogUtils.d(LOG_TAG, "getPostParams -> title: " + title + " content: " + content + " xsrf: " + xsrf);
        return params;
    }

}
