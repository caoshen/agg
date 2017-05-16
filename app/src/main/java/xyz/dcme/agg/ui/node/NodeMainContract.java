package xyz.dcme.agg.ui.node;

import java.util.List;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface NodeMainContract {

    interface Presenter extends BasePresenter {
        void load();
    }

    interface View extends BaseView<Presenter> {
        void showNodes(List<Node> nodes);

        void showIndicator(boolean isActive);

        void showErrorTip();
    }
}
