package xyz.dcme.agg.ui.reply;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface ReplyContract {

    interface Presenter extends BasePresenter {
        void start(String name);

        void refresh(String name);

        void load(String name, int page);
    }

    interface View extends BaseView<Presenter> {
        void showIndicator(boolean active);

        void showRefresh(List<Reply> replies);

        void showLoad(List<Reply> data);
    }
}
