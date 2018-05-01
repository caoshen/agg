package cn.okclouder.ovc.ui.node;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
                    if (!isNodeCountValid(n)) {
                        Toast.makeText(mContext, R.string.please_select_at_least_one_node, Toast.LENGTH_SHORT).show();
                        return;
                    }
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

    private boolean isNodeCountValid(Node n) {
        if (n.getSelected() == 0) {
            return true;
        }
        List<NodeCategory> categories = getDatas();
        if (categories == null) {
            return false;
        }
        for (NodeCategory category : categories) {
            List<Node> nodeList = category.getNodeList();
            if (nodeList == null) {
                continue;
            }
            for (Node node : nodeList) {
                if (node == n) {
                    continue;
                }
                if (node.getSelected() == 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
