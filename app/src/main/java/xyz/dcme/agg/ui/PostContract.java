package xyz.dcme.agg.ui;

import java.util.List;

/**
 * @author cs
 * @date 17-2-23
 */

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void onRefresh(List<String> data);
        void onLoadMore(List<String> data);
    }

    interface Presenter extends BasePresenter {
        void loadMore(int nextPage);
    }

}
