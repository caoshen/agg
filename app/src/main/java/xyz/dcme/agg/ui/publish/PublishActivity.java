package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.content.Intent;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;


public class PublishActivity extends BaseActivity {
    private static final String LOG_TAG = "PublishActivity";
    private static final String EXTRA_COMMENT_URL = "comment_url";

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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.publish_container, PublishFragment.newInstance())
                .commit();
    }

    public static void startPublish(Context context, String url) {
        Intent intent = new Intent(context, PublishActivity.class);
        intent.putExtra(EXTRA_COMMENT_URL, url);
        context.startActivity(intent);
    }
}
