package cn.okclouder.ovc.frag.node;

import android.content.Intent;
import android.os.Bundle;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;
import cn.okclouder.ovc.ui.node.Node;


public class NodeSelectActivity extends BaseFragmentActivity {
    public static final String KEY_SELECTED_NODE = "key_selected_node";

    @Override
    protected int getContextViewId() {
        return R.id.id_node_select_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Node selection = intent.getParcelableExtra(KEY_SELECTED_NODE);
        String tag = NodeSelectFragment.class.getSimpleName();
        getSupportFragmentManager().beginTransaction()
                .add(getContextViewId(), NodeSelectFragment.newInstance(selection), tag)
                .addToBackStack(tag)
                .commit();
    }

}
