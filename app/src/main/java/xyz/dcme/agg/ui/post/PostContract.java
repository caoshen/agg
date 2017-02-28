package xyz.dcme.agg.ui.post;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.BasePresenter;
import xyz.dcme.agg.ui.BaseView;

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void onRefresh(List<Post> data);
        void onLoadMore(List<Post> data);
    }

    interface Presenter extends BasePresenter {
        void loadMore(int nextPage);
    }

}
