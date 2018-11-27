package cn.okclouder.ovc.ui.personal.page;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

public interface PersonalInfoContract {

    interface Presenter extends BasePresenter {
        void loadDetail(String userName);
    }

    interface View extends BaseView<Presenter> {
        void showImage(String imageUrl);
    }
}
