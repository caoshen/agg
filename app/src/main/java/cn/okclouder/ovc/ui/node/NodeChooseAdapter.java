package cn.okclouder.ovc.ui.node;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Collections;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.widget.ItemDragHelperCallback;

public class NodeChooseAdapter extends CommonAdapter<Node>
        implements ItemDragHelperCallback.OnItemMoveListener {

    public NodeChooseAdapter(Context context, int layoutId, List<Node> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Node node, int position) {
    }

    @Override
    public boolean onItemMove(int from, int to) {
        Collections.swap(getDatas(), from, to);
        notifyItemMoved(from, to);
        return true;
    }

}
