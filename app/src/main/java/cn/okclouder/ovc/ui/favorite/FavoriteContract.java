package cn.okclouder.ovc.ui.favorite;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.model.Post;

public interface FavoriteContract {

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
