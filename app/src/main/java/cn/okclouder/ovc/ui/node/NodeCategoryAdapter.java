package cn.okclouder.ovc.ui.node;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.database.NodeDbHelper;
import cn.okclouder.ovc.widget.flowlayout.TagView;


public class NodeCategoryAdapter extends CommonAdapter<NodeCategory> {
    public NodeCategoryAdapter(Context context, int layoutId, List<NodeCategory> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, NodeCategory nodeCategory, int position) {
        holder.setText(R.id.category_text, nodeCategory.getName());
        FlexboxLayout flexBox = holder.getView(R.id.item_category);
        for (final Node n : nodeCategory.getNodeList()) {
            final TagView tagView = new TagView(mContext);
            final TextView tv = (TextView) mInflater.inflate(R.layout.item_subscribe_node, tagView, false);
            tv.setText(n.getTitle());
            tv.setDuplicateParentStateEnabled(true);
            tagView.addView(tv);
            tagView.setChecked(n.getSelected() == 1);
            tv.setTextColor(mContext.getResources().getColor(n.getSelected() == 1 ? R.color.theme_primary
                : R.color.black_50));
            tagView.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {
                    tagView.toggle();
                    if (tagView.isChecked()) {
                        tv.setTextColor(mContext.getResources().getColor(R.color.theme_primary));
                    } else {
                        tv.setTextColor(mContext.getResources().getColor(R.color.black_50));
                    }
                    n.setSelected(tagView.isChecked() ? 1 : 0);
                    NodeDbHelper.getInstance().updateNode(mContext, n);
                }
            });

            flexBox.addView(tagView);
        }
    }
}
