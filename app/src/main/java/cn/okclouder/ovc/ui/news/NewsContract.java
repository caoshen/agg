package cn.okclouder.ovc.ui.news;

import java.util.List;

import cn.okclouder.ovc.model.Post;
import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

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
