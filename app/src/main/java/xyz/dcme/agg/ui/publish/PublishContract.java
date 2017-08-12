package xyz.dcme.agg.ui.publish;

import java.io.File;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public interface PublishContract {

    interface Presenter extends BasePresenter {
        void publishArticle(String title, String content, String node);

        void publishComment(String content, String url);

        void preview();

        void uploadImage(File file);
    }

    interface View extends BaseView<Presenter> {
        void insertImage(String imageUrl, String imageName);

        void showUploadImageError(String response);

        void showUploadTips(boolean active);

        void sendArticleSuccess();

        void sendArticleFail();

        void sendCommentSuccess();

        void sendCommentFail();
    }
}
