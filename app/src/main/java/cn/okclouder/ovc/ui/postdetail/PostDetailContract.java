package cn.okclouder.ovc.ui.postdetail;

import android.content.Context;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {
        void start(String url);

        void load(String url, int page);

        void load(String url);

        void refresh(String url);

        void sendComment(String comment, String url);

        void addFavourite(String url);

        void like(String url);
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

        void showPostLike(String tips);

        void showLoginTips();
    }
}
