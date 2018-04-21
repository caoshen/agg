package xyz.dcme.agg.frag.node;

import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragmentActivity;


public class NodeAllCategoryActivity extends BaseFragmentActivity {
    @Override
    protected int getContextViewId() {
        return R.id.id_node_all_category_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QMUIStatusBarHelper.setStatusBarLightMode(this);

        NodeAllCategoryFragment fragment = new NodeAllCategoryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(getContextViewId(), fragment, NodeAllCategoryFragment.class.getSimpleName())
                .addToBackStack(NodeAllCategoryFragment.class.getSimpleName())
                .commit();
    }
}
