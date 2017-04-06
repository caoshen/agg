package xyz.dcme.agg.ui.personal;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface PersonalInfoDetailContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showDetails(List<Detail> details);
    }

    interface Presenter extends BasePresenter {

    }
}
