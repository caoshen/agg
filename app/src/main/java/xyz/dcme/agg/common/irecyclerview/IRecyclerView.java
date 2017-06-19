package xyz.dcme.agg.common.irecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class IRecyclerView extends RecyclerView {
    private boolean mLoadMoreEnabled;
    private OnScrollListener mLoadMoreScrollListener;
    private OnLoadMoreListener mLoadMoreListener;
    private View mLoadMoreView;

    public IRecyclerView(Context context) {
        this(context, null);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setLoadMoreEnabled(true);
    }

    public void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreEnabled = enabled;
        if (mLoadMoreEnabled) {
            if (mLoadMoreScrollListener == null) {
                mLoadMoreScrollListener = new OnLoadMoreScrollListener() {
                    @Override
                    public void onLoadMore(RecyclerView recyclerView) {
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoadMore(mLoadMoreView);
                        }
                    }
                };
            } else {
                removeOnScrollListener(mLoadMoreScrollListener);
            }
            addOnScrollListener(mLoadMoreScrollListener);
        } else {
            if (mLoadMoreView != null) {
                removeLoadMoreView();
            }
            if (mLoadMoreScrollListener != null) {
                removeOnScrollListener(mLoadMoreScrollListener);
            }
        }
    }

    private void removeLoadMoreView() {

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }
}
