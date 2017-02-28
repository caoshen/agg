package xyz.dcme.agg.ui.postdetail;

import xyz.dcme.agg.ui.BasePresenter;
import xyz.dcme.agg.ui.BaseView;

public interface PostDetailContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void onRefresh();
    }
}
