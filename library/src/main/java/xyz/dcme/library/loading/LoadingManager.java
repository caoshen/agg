package xyz.dcme.library.loading;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

public class LoadingManager {
    public static final int NO_LAYOUT_ID = 0;
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

        // Set callback
        listener.setEmptyEvent(mLoadingLayout.getEmpty());
        listener.setRetryEvent(mLoadingLayout.getError());
        listener.setLoadingEvent(mLoadingLayout.getLoading());

    }
}
