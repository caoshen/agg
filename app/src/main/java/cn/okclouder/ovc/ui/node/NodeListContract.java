package cn.okclouder.ovc.ui.node;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.model.Post;

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
