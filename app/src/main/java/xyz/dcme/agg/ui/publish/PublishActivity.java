package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.content.Intent;

import xyz.dcme.agg.R;
import xyz.dcme.library.base.BaseActivity;


public class PublishActivity extends BaseActivity {
    private static final String LOG_TAG = "PublishActivity";
    private static final String EXTRA_COMMENT_URL = "comment_url";
    private String mCommentUrl;

    @Override
    protected void getData() {
        super.getData();
        Intent intent = getIntent();
        if (intent != null) {
            mCommentUrl = intent.getStringExtra(EXTRA_COMMENT_URL);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.publish_container, PublishFragment.newInstance(mCommentUrl))
                .commit();
    }

    public static void startPublish(Context context, String url) {
        Intent intent = new Intent(context, PublishActivity.class);
        intent.putExtra(EXTRA_COMMENT_URL, url);
        context.startActivity(intent);
    }

    public static void startPublish(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }
}
