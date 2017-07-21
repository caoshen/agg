package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import xyz.dcme.agg.ui.publish.helper.CommentHelper;
import xyz.dcme.agg.ui.publish.helper.PostHelper;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.LogUtils;

public class PublishPresenter implements PublishContract.Presenter {
    private static final String LOG_TAG = "PublishPresenter";
    private PostHelper mPostHelper;
    private CommentHelper mCommentHelper;
    private PublishContract.View mView;
    private Context mContext;


    public PublishPresenter(PublishContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mContext = mView.getViewContext();
        mPostHelper = new PostHelper();
        mCommentHelper = new CommentHelper();
    }

    @Override
    public void start() {

    }

    @Override
    public void publishArticle(String title, String content, String node) {
        if (mContext == null) {
            LogUtils.e(LOG_TAG, "publishArticle -> context is null");
            return;
        }
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            LogUtils.e(LOG_TAG, "publishComment -> title or content is null");
            return;
        }

        if (!AccountUtils.hasActiveAccount(mContext)) {
            mView.startLogin();
            return;
        }

        mPostHelper.sendPost(title, content, node, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "publishArticle -> send error: " + e);
                mView.sendArticleFail();
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(LOG_TAG, "publishArticle -> send success: " + response);
                mView.sendArticleSuccess();
            }
        });
    }

    @Override
    public void publishComment(String content, String url) {
        if (mContext == null) {
            LogUtils.e(LOG_TAG, "publishComment -> context is null");
            return;
        }
        if (TextUtils.isEmpty(url)) {
            LogUtils.e(LOG_TAG, "publishComment -> url is null");
            return;
        }

        if (!AccountUtils.hasActiveAccount(mContext)) {
            mView.startLogin();
            return;
        }

        mCommentHelper.sendComment(content, url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "publishComment -> send error: " + e);
                mView.sendCommentFail();
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(LOG_TAG, "publishComment -> send success: " + response);
                mView.sendCommentSuccess();
            }
        });
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
