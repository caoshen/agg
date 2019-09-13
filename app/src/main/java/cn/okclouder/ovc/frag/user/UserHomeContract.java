package cn.okclouder.ovc.frag.user;

import java.util.List;

import cn.okclouder.library.base.BasePresenter;
import cn.okclouder.library.base.BaseView;
import cn.okclouder.ovc.ui.personal.detail.Detail;
import cn.okclouder.ovc.ui.personal.page.Count;

public class UserHomeContract {
    interface Presenter extends BasePresenter {
        void query(String name);
    }

    interface View extends BaseView<Presenter> {
        void showImage(String imageUrl);
        void setLoadingIndicator(boolean active);
        void showDetails(List<Detail> details);
        void showCount(Count count);
    }
}
