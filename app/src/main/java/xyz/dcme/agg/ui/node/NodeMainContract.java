package xyz.dcme.agg.ui.node;

import android.content.Context;

import java.util.List;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public interface NodeMainContract {

    interface Presenter extends BasePresenter {
        void load();
    }

    interface View extends BaseView<Presenter> {
        void showNodes(List<Node> nodes);

        void showIndicator(boolean isActive);

        void showErrorTip();

        Context getViewContext();
    }
}
