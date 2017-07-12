package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;

public class PublishPresenter implements PublishContract.Presenter {
    private static final String LOG_TAG = "PublishPresenter";
    private PublishContract.View mView;
    private Context mContext;


    public PublishPresenter(PublishContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mContext = mView.getViewContext();
    }

    @Override
    public void start() {

    }

    @Override
    public void publishArticle(String title, String content, String node) {
        if (mContext == null) {
            LogUtils.e(LOG_TAG, "sendReply -> context is null");
            return;
        }
        if (!AccountUtils.hasActiveAccount(mContext)) {
            mView.startLogin();
            return;
        }

        String url = getNodeUrl(node);
        publish(title, content, url);
    }

    private String getNodeUrl(String node) {
        return Constants.CREATE_POST_URL + node;
    }

    private void publish(final String title, final String content, final String url) {
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Map<String, String> params = getParams(response, title, content);
                postArticle(url, params);
            }
        });
    }

    private void postArticle(String url, Map<String, String> params) {
        if (params == null) {
            return;
        }

        HttpUtils.post(url, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(LOG_TAG, response);
            }
        });
    }

    private Map<String, String> getParams(String response, String title, String content) {
        Map<String, String> params = new HashMap<>();
        String xsrf = HttpUtils.findXsrf(response);

        params.put("title", title);
        params.put("content", content);
        params.put("_xsrf", xsrf);

        LogUtils.d(LOG_TAG, "getParams -> title: " + title + " content: " + content + " xsrf: " + xsrf);
        return params;
    }

    @Override
    public void publishComment(String content, String id) {

    }

    @Override
    public void preview() {

    }

    @Override
    public void uploadImage(File file) {
        String filePath = file.getAbsolutePath();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String url = "http://www.guanggoo.com/image_upload";

        mView.showUploadTips(true);
        OkHttpUtils.post()
                .addFile("files", fileName, file)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e(LOG_TAG, "error: " + e.toString());
                        mView.showUploadImageError(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(LOG_TAG, "response: " + response);
                        String imageUrl = null;
                        String imageName = null;
                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONArray files = resp.getJSONArray("files");
                            if (files != null && files.length() > 0) {
                                JSONObject file = files.getJSONObject(0);
                                imageUrl = file.getString("url");
                                imageName = file.getString("name");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(LOG_TAG, e.toString());
                            mView.showUploadImageError(response);
                        }

                        if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imageName)) {
                            mView.insertImage(imageUrl, imageName);
                            mView.showUploadTips(false);
                        } else {
                            if (TextUtils.isEmpty(imageUrl)) {
                                String errorMessage = ImageUploadError.getErrorMessage(imageName);
                                LogUtils.e(LOG_TAG, errorMessage);
                                mView.showUploadImageError(errorMessage);
                            }
                        }
                    }
                });
    }
}
