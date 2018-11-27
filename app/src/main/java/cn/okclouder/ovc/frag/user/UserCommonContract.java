package cn.okclouder.ovc.frag.user;

import java.util.List;

import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.reply.Reply;
import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

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
