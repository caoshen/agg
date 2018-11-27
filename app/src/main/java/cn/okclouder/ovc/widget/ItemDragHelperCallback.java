package cn.okclouder.ovc.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemDragHelperCallback extends ItemTouchHelper.Callback {
    private OnItemMoveListener mOnItemMoveListener;

    public ItemDragHelperCallback(OnItemMoveListener listener) {
        mOnItemMoveListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = setDragFlags(recyclerView);
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    private int setDragFlags(RecyclerView recyclerView) {
        int dragsFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager || lm instanceof StaggeredGridLayoutManager) {
            dragsFlags = dragsFlags | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return dragsFlags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        return !isDiffItemViewType(viewHolder, target)
                && mOnItemMoveListener.onItemMove(from, to);
    }

    private boolean isDiffItemViewType(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() != target.getItemViewType();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public interface OnItemMoveListener {
        boolean onItemMove(int from, int to);
    }
}
