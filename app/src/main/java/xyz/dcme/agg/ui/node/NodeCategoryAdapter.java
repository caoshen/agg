package xyz.dcme.agg.ui.node;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.widget.flowlayout.TagView;


public class NodeCategoryAdapter extends CommonAdapter<NodeCategory> {
    public NodeCategoryAdapter(Context context, int layoutId, List<NodeCategory> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, NodeCategory nodeCategory, int position) {
        holder.setText(R.id.category_text, nodeCategory.getName());
        FlexboxLayout flexBox = holder.getView(R.id.item_category);
        for (Node n : nodeCategory.getNodeList()) {
            final TagView tagView = new TagView(mContext);
            final TextView tv = (TextView) mInflater.inflate(R.layout.item_subscribe_node, tagView, false);
            tv.setText(n.getTitle());
            tv.setDuplicateParentStateEnabled(true);
            tagView.addView(tv);
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
                }
            });

            flexBox.addView(tagView);
        }
    }
}
