package xyz.dcme.agg.ui.postdetail;

import android.content.Context;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {
        void loadDetail(String url);

        void sendReply(String comment, String url);
    }

    interface View extends BaseView<Presenter> {
        void showIndicator(boolean isActive);

        void onRefresh(List<PostDetailItem> data);

        void onLoadMore(List<PostDetailItem> data);

        void addComment(String comment);

        void sendCommentFailed();

        void showCommentIndicator(boolean b);

        void setCommentSuccess();

        void startLogin();

        Context getViewContext();
    }
}
