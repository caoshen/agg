package xyz.dcme.agg.ui.post;

import java.util.List;

import xyz.dcme.agg.BaseView;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.BasePresenter;

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void onRefresh(List<Post> data);
        void onLoadMore(List<Post> data);

        void onError();
    }

    interface Presenter extends BasePresenter {
        void loadMore(int nextPage);
    }

}
