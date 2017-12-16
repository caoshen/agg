package xyz.dcme.library.loading;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

public class LoadingManager {
    public static final int NO_LAYOUT_ID = 0;
    public static final int NO_EMPTY_ID = NO_LAYOUT_ID;
    public static final int NO_ERROR_ID = NO_LAYOUT_ID;
    public static final int NO_LOADING_ID = NO_LAYOUT_ID;

    private static final OnLoadingListener DEFAULT_LOADING_LISTENER = new OnLoadingListener() {
        @Override
        public void setRetryEvent(View retryEvent) {

        }
    };
    private LoadingLayout mLoadingLayout;

    public LoadingManager(Object activityOrFragmentOrView, OnLoadingListener listener) {
        if (listener == null) {
            listener = DEFAULT_LOADING_LISTENER;
        }
        Context context;
        ViewGroup parentView = null;

        // Init params
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            parentView = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            View view = fragment.getView();
            if (view != null) {
                parentView = (ViewGroup) view.getParent();
            }
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            context = view.getContext();
            parentView = (ViewGroup) view.getParent();
        } else {
            throw new IllegalArgumentException("1st argument must be activity, fragment or view.");
        }
        if (parentView == null) {
            return;
        }

        // Replace with loading layout
        View oldView;
        int oldViewIndex = 0;
        if (activityOrFragmentOrView instanceof View) {
            oldView = (View) activityOrFragmentOrView;
            int childCount = parentView.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                if (parentView.getChildAt(i) == oldView) {
                    oldViewIndex = i;
                    break;
                }
            }
        } else {
            oldView = parentView.getChildAt(0);
        }
        ViewGroup.LayoutParams lp = oldView.getLayoutParams();
        parentView.removeView(oldView);
        mLoadingLayout = new LoadingLayout(context);
        mLoadingLayout.setContent(oldView);
        parentView.addView(mLoadingLayout, oldViewIndex, lp);

        setupLoadingLayout(listener, mLoadingLayout);
        setupErrorLayout(listener, mLoadingLayout);
        setupEmptyLayout(listener, mLoadingLayout);
        // Set callback
        listener.setEmptyEvent(mLoadingLayout.getEmpty());
        listener.setRetryEvent(mLoadingLayout.getError());
        listener.setLoadingEvent(mLoadingLayout.getLoading());

    }

    private void setupEmptyLayout(OnLoadingListener listener, LoadingLayout loadingLayout) {
        if (listener.isSetEmptyLayout()) {
            int id = listener.generateEmptyLayoutId();
            if (id != NO_LAYOUT_ID) {
                loadingLayout.setEmpty(id);
            } else {
                loadingLayout.setEmpty(listener.generateEmptyLayout());
            }
        } else {
            if (NO_EMPTY_ID != NO_LAYOUT_ID) {
                loadingLayout.setEmpty(NO_EMPTY_ID);
            }
        }
    }

    private void setupErrorLayout(OnLoadingListener listener, LoadingLayout loadingLayout) {
        if (listener.isSetErrorLayout()) {
            int id = listener.generateErrorLayoutId();
            if (id != NO_LAYOUT_ID) {
                loadingLayout.setError(id);
            } else {
                loadingLayout.setError(listener.generateErrorLayout());
            }
        } else {
            if (NO_ERROR_ID != NO_LAYOUT_ID) {
                loadingLayout.setError(NO_ERROR_ID);
            }
        }
    }

    private void setupLoadingLayout(OnLoadingListener listener, LoadingLayout loadingLayout) {
        if (listener.isSetLoadingLayout()) {
            int id = listener.generateLoadingLayoutId();
            if (id != NO_LAYOUT_ID) {
                loadingLayout.setLoading(id);
            } else {
                loadingLayout.setLoading(listener.generateLoadingLayout());
            }
        } else {
            if (NO_LOADING_ID != NO_LAYOUT_ID) {
                loadingLayout.setLoading(NO_LOADING_ID);
            }
        }
    }

    public void showLoading() {
        mLoadingLayout.showLoading();
    }

    public void showContent() {
        mLoadingLayout.showContent();
    }

    public void showError() {
        mLoadingLayout.showError();
    }

    public void showEmpty() {
        mLoadingLayout.showError();
    }

    public static LoadingManager generate(Object activityOrFragmentOrView, OnLoadingListener listener) {
        return new LoadingManager(activityOrFragmentOrView, listener);
    }
}
