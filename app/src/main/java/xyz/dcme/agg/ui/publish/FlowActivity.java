package xyz.dcme.agg.ui.publish;

import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.util.UIUtil;


public class FlowActivity extends BaseActivity {
    String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};
    private FlexboxLayout mFlexBoxLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flow;
    }

    @Override
    public void initView() {
        mFlexBoxLayout = (FlexboxLayout) findViewById(R.id.flex);
        for (String tag : tags) {
            mFlexBoxLayout.addView(createNewFlexItemTextView(tag));
        }
    }

    private View createNewFlexItemTextView(final String tag) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(tag);
        textView.setTextSize(12);
        textView.setBackgroundResource(R.drawable.tag_states);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FlowActivity.this, "click " + tag, Toast.LENGTH_LONG).show();
            }
        });
        int padding = UIUtil.dp2px(this, 4);
        ViewCompat.setPaddingRelative(textView, padding, padding, padding, padding);
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = UIUtil.dp2px(this, 6);
        lp.setMargins(margin, margin, margin, 0);
        textView.setLayoutParams(lp);
        return textView;
    }
}
