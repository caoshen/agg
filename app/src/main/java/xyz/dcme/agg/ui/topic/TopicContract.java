package xyz.dcme.agg.ui.topic;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;
import xyz.dcme.agg.model.Post;

public interface TopicContract {

    interface Presenter extends BasePresenter {
        void start(String name);

        void refresh(String name);

        void load(String name, int page);

    }

    interface View extends BaseView<Presenter> {
        void showIndicator(boolean active);

        void showRefresh(List<Post> data);

        void showLoad(List<Post> data);
    }
}
