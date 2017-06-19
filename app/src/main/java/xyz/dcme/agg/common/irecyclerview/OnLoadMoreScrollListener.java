package xyz.dcme.agg.common.irecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    public abstract void onLoadMore(RecyclerView recyclerView);

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int visibleItemCount = lm.getChildCount();

        boolean triggerCondition = (visibleItemCount > 0)
                && (newState == RecyclerView.SCROLL_STATE_IDLE)
                && (canTriggerLoadMore(recyclerView));

        if (triggerCondition) {
            onLoadMore(recyclerView);
        }
    }

    private boolean canTriggerLoadMore(RecyclerView recyclerView) {
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildLayoutPosition(lastChild);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int totalCount = lm.getItemCount();

        return totalCount - 1 == position;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
