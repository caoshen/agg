package xyz.dcme.agg.ui.favorite;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;
import xyz.dcme.agg.model.Post;

public interface FavoriteContract {

    interface Presenter extends BasePresenter {
        void load(String name);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showFav(List<Post> posts);
        void showNoData();
    }
}
