package cn.okclouder.ovc.ui.publish;

import java.io.File;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

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
