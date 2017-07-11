package xyz.dcme.agg.ui.personal.page;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface PersonalInfoContract {

    interface Presenter extends BasePresenter {
        void loadDetail(String userName);
    }

    interface View extends BaseView<Presenter> {
        void showImage(String imageUrl);
    }
}
