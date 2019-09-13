package cn.okclouder.ovc.frag.write;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;
import cn.okclouder.ovc.ui.node.Node;


public class WriteActivity extends BaseFragmentActivity {
    private static final String EXTRA_COMMENT_URL = "comment_url";
    private static final String EXTRA_SELECTED_NODE = "selected_node";
    private String mCommentUrl;
    private Node mSelectedNode;

    public static void startPublish(Context context, String url) {
        Intent intent = new Intent(context, WriteActivity.class);
        intent.putExtra(EXTRA_COMMENT_URL, url);
        context.startActivity(intent);
    }

    public static void startPublish(Context context) {
        Intent intent = new Intent(context, WriteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContextViewId() {
        return R.id.id_write_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        Intent intent = getIntent();
        mCommentUrl = intent.getStringExtra(EXTRA_COMMENT_URL);
        mSelectedNode = intent.getParcelableExtra(EXTRA_SELECTED_NODE);

        String tag = WriteFragment.class.getSimpleName();
        getSupportFragmentManager().beginTransaction()
                .add(getContextViewId(), WriteFragment.newInstance(mCommentUrl, mSelectedNode), tag)
                .addToBackStack(tag)
                .commit();
    }
}
