package xyz.dcme.agg.ui.node;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;

public class NodeChooseAdapter extends CommonAdapter<Node> {
    public NodeChooseAdapter(Context context, int layoutId, List<Node> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Node node, int position) {
        holder.setText(R.id.item_node_choose_text, node.getTitle());
    }
}
