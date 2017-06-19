package xyz.dcme.agg.common.irecyclerview;

import android.support.v7.widget.RecyclerView;


public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    public abstract void onLoadMore(RecyclerView recyclerView);

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
