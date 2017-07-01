package xyz.dcme.agg.ui.publish;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface PublishContract {

    interface Presenter extends BasePresenter {
        void publishArticle(String title, String content, String node);

        void publishComment(String content, String id);

        void preview();
    }

    interface View extends BaseView<Presenter> {

    }
}
