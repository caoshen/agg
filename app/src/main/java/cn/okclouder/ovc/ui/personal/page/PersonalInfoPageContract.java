package cn.okclouder.ovc.ui.personal.page;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

public interface PersonalInfoPageContract {
    interface Presenter extends BasePresenter {
        void load(String name);
    }

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void show(Count count);
    }
}
