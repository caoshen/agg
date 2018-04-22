package cn.okclouder.ovc.ui.node;

import android.content.Context;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;

public interface NodeChooseContract {
    interface Presenter extends BasePresenter {
        void load();

        void onItemSwap(List<Node> nodes);

        void onItemAdd();

        void onItemRemove();

        void onItemChange(List<Node> curNodes, List<Node> moreNodes);
    }

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator(boolean active);

        void showError();

        void showCurNode(List<Node> curNodes);

        void showMoreNode(List<Node> moreNodes);

        Context getViewContext();
    }
}