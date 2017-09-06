package xyz.dcme.agg.ui.node;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import xyz.dcme.agg.R;


public class NodeView extends FrameLayout {

    public NodeView(@NonNull Context context) {
        this(context, null);
    }

    public NodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomView(context);
    }

    private void initCustomView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_subscribe_node, this);
    }

    public void setNodeName(String name) {
//        TextView nodeText = (TextView) findViewById(R.id.node_name);
//        nodeText.setText(name);
    }
}
