package xyz.dcme.agg.ui.personal.page;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public interface PersonalInfoContract {

    interface Presenter extends BasePresenter {
        void loadDetail(String userName);
    }

    interface View extends BaseView<Presenter> {
        void showImage(String imageUrl);
    }
}
