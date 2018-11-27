package cn.okclouder.ovc.frag.node;

import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;


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
