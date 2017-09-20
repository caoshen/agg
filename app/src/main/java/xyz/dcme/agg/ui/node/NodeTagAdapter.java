package xyz.dcme.agg.ui.node;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import xyz.dcme.library.widget.BaseTagAdapter;
import xyz.dcme.library.widget.BaseTagView;


public class NodeTagAdapter extends BaseTagAdapter<NodeTagView, Node> {

    public NodeTagAdapter(Context context, List<Node> data) {
        super(context, data);
    }

    public NodeTagAdapter(Context context, List<Node> data, List<Node> selection) {
        super(context, data, selection);
    }

    @Override
    protected boolean isItemSame(NodeTagView tagView, Node item) {
        String tagTitle = tagView.getItem().getTitle();
        String itemTitle = item.getTitle();
        return !TextUtils.isEmpty(tagTitle) && !TextUtils.isEmpty(itemTitle)
                && tagTitle.equals(itemTitle);
    }

    @Override
    protected boolean isItemNull(Node item) {
        String itemTitle = item.getTitle();
        return TextUtils.isEmpty(itemTitle);
    }

    @Override
    protected BaseTagView<Node> addTag(Node node) {
        NodeTagView nodeTagView = new NodeTagView(getContext());
        nodeTagView.setPadding(25, 25, 25, 25);
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 20, 20, 20);
        nodeTagView.setLayoutParams(lp);
        TextView textView = nodeTagView.getTextView();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.CENTER);
        textView.setText(node.getTitle());
        nodeTagView.setDefaultDrawable(mDefaultDrawable);
        nodeTagView.setSelectedDrawable(mSelectedDrawable);
        nodeTagView.setDefaultTextColor(mDefaultTextColor);
        nodeTagView.setSelectedTextColor(mSelectedTextColor);
        nodeTagView.setItem(node);

        return nodeTagView;
    }
}
