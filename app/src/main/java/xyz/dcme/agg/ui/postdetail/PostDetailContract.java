package xyz.dcme.agg.ui.postdetail;

import android.content.Context;

import java.util.List;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {
        void start(String url);

        void load(String url, int page);

        void load(String url);

        void refresh(String url);

        void sendComment(String comment, String url);

        void addFavourite(String url);
    }

    interface View extends BaseView<Presenter> {
        void showIndicator(boolean isActive);

        void showRefreshingIndicator(boolean isActive);

        void showRefresh(List<PostDetailItem> data);

        void onLoadMore(List<PostDetailItem> data);

        void sendCommentFailed();

        void showCommentIndicator(boolean b);

        void setCommentSuccess();

        void startLogin();

        Context getViewContext();

        void showFavouriteAddTips(String tips);
    }
}
