package cn.okclouder.ovc.ui.hot;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.model.Post;

public interface HotContract {
    interface Presenter extends BasePresenter {
        void refresh();
    }

    interface View extends BaseView<Presenter> {
        void showRefresh(List<Post> data);

        void showIndicator(boolean isActive);

        void showError();
    }
}
