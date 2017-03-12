package xyz.dcme.agg.ui.postdetail;

import java.util.List;

import xyz.dcme.agg.ui.BasePresenter;
import xyz.dcme.agg.ui.BaseView;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {
        void loadDetail(String url);

        void sendReply(String comment);
    }

    interface View extends BaseView<Presenter> {
        void onRefresh(List<PostDetailItem> data);
        void onLoadMore(List<PostDetailItem> data);

        void addComment(String comment);
    }
}
