package xyz.dcme.agg.ui.notify;

import java.util.List;

import xyz.dcme.library.base.BasePresenter;
import xyz.dcme.library.base.BaseView;

public class MessageContract {
    public interface Presenter extends BasePresenter {
        void refresh();

        void load(int page);
    }

    public interface View extends BaseView<Presenter> {
        void showRefresh(List<Message> messages);

        void showLoad(List<Message> messages);

        void showIndicator(boolean isActive);
    }
}
