package xyz.dcme.agg.ui.node;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.library.base.BaseActivity;
import xyz.dcme.library.widget.IItemSelectedListener;
import xyz.dcme.library.widget.TagFlowLayout;


public class TagSelectActivity extends BaseActivity {

    private TagFlowLayout mTagFlowLayout;
    private NodeTagAdapter mTagAdapter;
    private List<Node> mData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag_select;
    }

    @Override
    public void initView() {
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.flow);
        mTagAdapter = new NodeTagAdapter(this, mData);
        mTagAdapter.setSelectedListener(new IItemSelectedListener<Node>() {
            @Override
            public void onItemSelect(List<Node> data) {
                Toast.makeText(TagSelectActivity.this, "选择" + data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mTagFlowLayout.setAdapter(mTagAdapter);
    }
}
