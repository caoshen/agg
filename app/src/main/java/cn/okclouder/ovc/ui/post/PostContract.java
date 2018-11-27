package cn.okclouder.ovc.ui.post;

import java.util.List;

import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.library.base.BasePresenter;

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
