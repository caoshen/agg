package xyz.dcme.agg.frag.user;

import java.util.List;

import xyz.dcme.agg.ui.personal.detail.Detail;
import xyz.dcme.agg.ui.personal.page.Count;
import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public class UserHomeContract {
    interface Presenter extends BasePresenter {
        void query(String name);
    }

    interface View extends BaseView<Presenter> {
        void showImage(String imageUrl);
        void setLoadingIndicator(boolean active);
        void showDetails(List<Detail> details);
        void showCount(Count count);
    }
}
