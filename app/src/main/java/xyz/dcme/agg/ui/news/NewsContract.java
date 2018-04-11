package xyz.dcme.agg.ui.news;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public interface NewsContract {
    interface Presenter extends BasePresenter {
        void refresh(String tab);

        void load(String tab, int page);
    }

    interface View extends BaseView<Presenter> {
        void showRefresh(List<Post> data);

        void showLoad(List<Post> data);

        void showIndicator(boolean isActive);

        void showError();
    }
}
