package xyz.dcme.agg.ui.hot;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

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
