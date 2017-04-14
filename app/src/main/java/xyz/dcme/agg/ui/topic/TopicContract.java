package xyz.dcme.agg.ui.topic;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface TopicContract {

    interface Presenter extends BasePresenter {
        void load(String name);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showTopics(List<Topic> topics);
    }
}
