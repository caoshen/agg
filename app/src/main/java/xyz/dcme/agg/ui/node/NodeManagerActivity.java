package xyz.dcme.agg.ui.node;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import xyz.dcme.agg.R;
import xyz.dcme.agg.widget.flowlayout.TagView;
import xyz.dcme.library.base.BaseActivity;


public class NodeManagerActivity extends BaseActivity {

    private FlexboxLayout mCategoryLayout;
    private LayoutInflater mInflater;
    private String[] tech = {"IT技术", "汽车", "硬件数码", "金融财经", "生命科学", "法律法规", "创业创客"};

    @Override
    public int getLayoutId() {
        return R.layout.item_subscribe;
    }

    @Override
    public void initView() {
        mInflater = LayoutInflater.from(this);
//        addCategory(R.id.item_category, tech);
    }

    private void addCategory(int categoryLayout, String[] nodes) {
        mCategoryLayout = (FlexboxLayout) findViewById(categoryLayout);
        for (String t : nodes) {
            final TagView tagView = new TagView(this);
            final TextView tv = (TextView) mInflater.inflate(R.layout.item_subscribe_node, tagView, false);
            tv.setText(t);
            tv.setDuplicateParentStateEnabled(true);
            tagView.addView(tv);
            tagView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagView.toggle();
                    if (tagView.isChecked()) {
                        tv.setTextColor(getResources().getColor(R.color.theme_primary));
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.black_50));
                    }
                }
            });

            mCategoryLayout.addView(tagView);
        }
    }
}
