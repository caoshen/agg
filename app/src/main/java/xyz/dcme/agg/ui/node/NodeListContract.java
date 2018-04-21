package xyz.dcme.agg.ui.node;

import java.util.List;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;
import xyz.dcme.agg.model.Post;

public interface NodeListContract {
    interface Presenter extends BasePresenter {
        void start(String nodeName);

        void refresh(String nodeName);

        void load(String nodeName, int page);
    }

    interface View extends BaseView<Presenter> {
        void showRefresh(List<Post> data);

        void showLoad(List<Post> data);

        void startLogin();

        void showIndicator(boolean isActive);

        void showError();

        void showLoginTips();
    }
}
