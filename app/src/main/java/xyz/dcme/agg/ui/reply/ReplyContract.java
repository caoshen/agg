package xyz.dcme.agg.ui.reply;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface ReplyContract {

    interface Presenter extends BasePresenter {
        void load(String name);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showReplies(List<Reply> replies);
        void showNoReplies();
    }
}
