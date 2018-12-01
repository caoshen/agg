package cn.okclouder.ovc.ui.publish;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

public interface PublishContract {

    interface Presenter extends BasePresenter {
        void publishArticle(String title, String content, String node);

        void publishComment(String content, String url);

    }

    interface View extends BaseView<Presenter> {

        void sendArticleSuccess();

        void sendArticleFail();

        void sendCommentSuccess();

        void sendCommentFail();
    }
}
