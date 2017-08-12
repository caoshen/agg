package xyz.dcme.agg.ui.publish;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import xyz.dcme.agg.ui.publish.helper.CommentHelper;
import xyz.dcme.agg.ui.publish.helper.ImageUpLoadListener;
import xyz.dcme.agg.ui.publish.helper.ImageUploadHelper;
import xyz.dcme.agg.ui.publish.helper.PostHelper;
import xyz.dcme.library.util.LogUtils;

public class PublishPresenter implements PublishContract.Presenter {
    private static final String LOG_TAG = "PublishPresenter";
    private ImageUploadHelper mUploadHelper;
    private PostHelper mPostHelper;
    private CommentHelper mCommentHelper;
    private PublishContract.View mView;


    public PublishPresenter(PublishContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mPostHelper = new PostHelper();
        mCommentHelper = new CommentHelper();
        mUploadHelper = new ImageUploadHelper();
    }

    @Override
    public void start() {

    }

    @Override
    public void publishArticle(String title, String content, String node) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            LogUtils.e(LOG_TAG, "publishComment -> title or content is null");
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
        if (TextUtils.isEmpty(url)) {
            LogUtils.e(LOG_TAG, "publishComment -> url is null");
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
        mView.showUploadTips(true);

        mUploadHelper.uploadImage(file, new ImageUpLoadListener() {
            @Override
            public void onError(String err) {
                mView.showUploadImageError(err);
            }

            @Override
            public void onResponse(String imageName, String imageUrl) {
                mView.insertImage(imageUrl, imageName);
                mView.showUploadTips(false);
            }
        });
    }
}
