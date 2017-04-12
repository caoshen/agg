package xyz.dcme.agg.ui.personal.page;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface PersonalInfoPageContract {
    interface Presenter extends BasePresenter {
        void load(String name);
    }

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void show(Count count);
    }
}
