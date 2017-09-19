package xyz.dcme.agg.ui.node;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.NodeDbHelper;
import xyz.dcme.library.base.BaseActivity;
import xyz.dcme.library.widget.IItemSelectedListener;
import xyz.dcme.library.widget.TagFlowLayout;


public class TagSelectActivity extends BaseActivity {

    public static final String KEY_SELECTED_TAG = "key_selected_tag";
    private TagFlowLayout mTagFlowLayout;
    private NodeTagAdapter mTagAdapter;
    private List<Node> mData = new ArrayList<>();

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
                    args.putExtra(KEY_SELECTED_TAG, data.get(0));
                    setResult(Activity.RESULT_OK, args);
                }
            }
        });
        mTagFlowLayout.setAdapter(mTagAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tag_select);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
