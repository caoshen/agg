package xyz.dcme.agg.ui.node;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
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
        mData = getNodes(R.array.fixed_node_name, R.array.fixed_node_title);
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.flow);
        mTagAdapter = new NodeTagAdapter(this, mData);
        mTagAdapter.setSelectedListener(new IItemSelectedListener<Node>() {
            @Override
            public void onItemSelect(List<Node> data) {
                Toast.makeText(TagSelectActivity.this, "选择" + data.toString(), Toast.LENGTH_SHORT).show();
                if (!data.isEmpty()) {
                    Intent args = new Intent();
                    args.putExtra(KEY_SELECTED_TAG, data.get(0));
                    setResult(Activity.RESULT_OK, args);
                }
            }
        });
        mTagFlowLayout.setAdapter(mTagAdapter);
    }

    private List<Node> getNodes(int nodeNameArray, int nodeTitleArray) {
        String[] names = mContext.getResources().getStringArray(nodeNameArray);
        String[] titles = mContext.getResources().getStringArray(nodeTitleArray);
        List<Node> nodes = new ArrayList<>();

        if (names.length != titles.length) {
            return nodes;
        }
        for (int i = 0; i < names.length; ++i) {
            Node n = new Node(names[i], titles[i]);
            n.setFix(1);
            nodes.add(n);
        }
        return nodes;
    }
}
