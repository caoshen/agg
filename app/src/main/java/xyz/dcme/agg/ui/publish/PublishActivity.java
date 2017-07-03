package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.content.Intent;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;


public class PublishActivity extends BaseActivity {
    private static final String LOG_TAG = "PublishActivity";

    public static void startPublish(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        getToolbar();
    }

}
