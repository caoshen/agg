package xyz.dcme.agg.frag.user;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.reply.Reply;
import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public class UserCommonContract {
    interface View extends BaseView<Presenter> {
        void showIndicator(boolean active);

        void showRefresh(List<Post> data);

        void showLoad(List<Post> data);

        void showRefreshedReply(List<Reply> data);

        void showLoadedReply(List<Reply> data);

    }

    interface Presenter extends BasePresenter {
        void start(String name);

        void refresh(String name);

        void load(String name, int page);
    }
}
