package xyz.dcme.agg.ui;

import java.util.List;

import xyz.dcme.agg.model.Post;

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void onRefresh(List<Post> data);
        void onLoadMore(List<Post> data);
    }

    interface Presenter extends BasePresenter {
        void loadMore(int nextPage);
    }

}
