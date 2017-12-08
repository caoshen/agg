package xyz.dcme.library.loading;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class LoadingLayout extends FrameLayout {
    private View mContent;
    private View mError;
    private View mLoading;
    private View mEmpty;
    private LayoutInflater mLayoutInflater;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public View getContent() {
        return mContent;
    }

    public View getError() {
        return mError;
    }

    public View getLoading() {
        return mLoading;
    }

    public View getEmpty() {
        return mEmpty;
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void showContent() {
        if (isMainThread()) {
            showView(mContent);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mContent);
                }
            });
        }
    }

    public void showError() {
        if (isMainThread()) {
            showView(mError);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mError);
                }
            });
        }
    }

    public void showLoading() {
        if (isMainThread()) {
            showView(mLoading);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mLoading);
                }
            });
        }
    }

    public void showEmpty() {
        if (isMainThread()) {
            showView(mEmpty);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mEmpty);
                }
            });
        }
    }

    private void showView(View view) {
        if (view == null) {
            return;
        }
        if (view == mContent) {
            mContent.setVisibility(View.VISIBLE);
            if (mEmpty != null) {
                mEmpty.setVisibility(View.GONE);
            }
            if (mError != null) {
                mError.setVisibility(View.GONE);
            }
            if (mLoading != null) {
                mLoading.setVisibility(View.GONE);
            }
        } else if (view == mEmpty) {
            mEmpty.setVisibility(View.VISIBLE);
            if (mContent != null) {
                mContent.setVisibility(View.GONE);
            }
            if (mError != null) {
                mError.setVisibility(View.GONE);
            }
            if (mLoading != null) {
                mLoading.setVisibility(View.GONE);
            }
        } else if (view == mError) {
            mError.setVisibility(View.VISIBLE);
            if (mContent != null) {
                mContent.setVisibility(View.GONE);
            }
            if (mEmpty != null) {
                mEmpty.setVisibility(View.GONE);
            }
            if (mLoading != null) {
                mLoading.setVisibility(View.GONE);
            }
        } else if (view == mLoading) {
            mLoading.setVisibility(View.VISIBLE);
            if (mContent != null) {
                mContent.setVisibility(View.GONE);
            }
            if (mEmpty != null) {
                mEmpty.setVisibility(View.GONE);
            }
            if (mError != null) {
                mError.setVisibility(View.GONE);
            }
        }
    }

    public View setContent(int layoutId) {
        return setContent(mLayoutInflater.inflate(layoutId, this, false));
    }

    public View setError(int layoutId) {
        return setError(mLayoutInflater.inflate(layoutId, this, false));

    }

    public View setEmpty(int layoutId) {
        return setEmpty(mLayoutInflater.inflate(layoutId, this, false));
    }

    public View setLoading(int layoutId) {
        return setLoading(mLayoutInflater.inflate(layoutId, this, false));
    }

    public View setContent(View view) {
        View content = mContent;
        removeView(content);
        addView(view);
        mContent = view;
        return mContent;
    }

    public View setEmpty(View view) {
        View empty = mEmpty;
        removeView(empty);
        addView(view);
        mEmpty = view;
        return mEmpty;
    }

    public View setError(View view) {
        View error = mError;
        removeView(error);
        addView(view);
        mError = view;
        return mError;
    }

    public View setLoading(View view) {
        View loadingView = mLoading;
        removeView(loadingView);
        addView(view);
        mLoading = view;
        return mLoading;
    }
}
