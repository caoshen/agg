package xyz.dcme.agg.ui.publish;

import android.content.Context;

import java.io.File;

import xyz.dcme.agg.BasePresenter;
import xyz.dcme.agg.BaseView;

public interface PublishContract {

    interface Presenter extends BasePresenter {
        void publishArticle(String title, String content, String node);

        void publishComment(String content, String id);

        void preview();

        void uploadImage(File file);
    }

    interface View extends BaseView<Presenter> {
        void insertImage(String imageUrl, String imageName);

        Context getViewContext();

        void startLogin();
    }
}
