package xyz.dcme.agg.ui.node;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;
import xyz.dcme.agg.model.Post;

public interface NodeListContract {
    interface Presenter extends BasePresenter {
        void start(String nodeName);

        void load(String nodeName, int page);
    }

    interface View extends BaseView<Presenter> {
        void onRefresh(List<Post> data);

        void onLoad(List<Post> data);

        void startLogin();
    }
}
