package cn.okclouder.ovc.ui.personal.detail;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

public interface PersonalInfoDetailContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showDetails(List<Detail> details);
    }

    interface Presenter extends BasePresenter {
        void load(String name);
    }
}
