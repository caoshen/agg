package cn.okclouder.library.loading;

import android.view.View;

public abstract class OnLoadingListener {
    public abstract void setRetryEvent(View retryEvent);

    public void setLoadingEvent(View loadingEvent) {

    }

    public void setEmptyEvent(View emptyEvent) {

    }

    public int generateLoadingLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateErrorLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateErrorLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != LoadingManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetErrorLayout() {
        if (generateErrorLayoutId() != LoadingManager.NO_LAYOUT_ID || generateErrorLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != LoadingManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }


}
