package cn.okclouder.ovc.ui.node;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.database.NodeDbHelper;
import cn.okclouder.library.base.BaseActivity;
import cn.okclouder.library.widget.IItemSelectedListener;
import cn.okclouder.library.widget.TagFlowLayout;


public class TagSelectActivity extends BaseActivity {

    public static final String KEY_SELECTED_TAG = "key_selected_tag";
    private TagFlowLayout mTagFlowLayout;
    private NodeTagAdapter mTagAdapter;
    private List<Node> mData = new ArrayList<>();
    private Node mSelection;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag_select;
    }

    @Override
    public void initView() {
        mData = NodeDbHelper.getInstance().queryAllNodes(this);
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.flow);
        mTagAdapter = new NodeTagAdapter(this, mData);
        mTagAdapter.setSelectedListener(new IItemSelectedListener<Node>() {
            @Override
            public void onItemSelect(List<Node> data) {
                if (!data.isEmpty()) {
                    Intent args = new Intent();
                    mSelection = data.get(0);
                    args.putExtra(KEY_SELECTED_TAG, mSelection);
                    setResult(Activity.RESULT_OK, args);
                }
            }
        });
        mTagFlowLayout.setAdapter(mTagAdapter);

        if (null != mSelection) {
            mTagAdapter.enterSingleMode(mSelection);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tag_select);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void getData() {
        Intent intent = getIntent();
        mSelection = intent.getParcelableExtra(KEY_SELECTED_TAG);
    }
}
