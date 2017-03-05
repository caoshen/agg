package xyz.dcme.agg.ui.postdetail;

import java.util.List;

import xyz.dcme.agg.model.PostComment;
import xyz.dcme.agg.ui.BasePresenter;
import xyz.dcme.agg.ui.BaseView;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {
        void loadDetail(String url);
    }

    interface View extends BaseView<Presenter> {
        void onRefresh(List<PostComment> data);
        void onLoadMore(List<PostComment> data);
    }
}
