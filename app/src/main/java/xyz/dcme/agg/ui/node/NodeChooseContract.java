package xyz.dcme.agg.ui.node;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface NodeChooseContract {
    interface Presenter extends BasePresenter {
        void load();

        void onItemSwap();

        void onItemAdd();

        void onItemRemove();
    }

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator(boolean active);

        void showError();

        void showMyNode();

        void showMoreNode();
    }
}
