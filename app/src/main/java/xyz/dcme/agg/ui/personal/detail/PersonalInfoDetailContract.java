package xyz.dcme.agg.ui.personal.detail;

import java.util.List;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public interface PersonalInfoDetailContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showDetails(List<Detail> details);
    }

    interface Presenter extends BasePresenter {
        void load(String name);
    }
}
