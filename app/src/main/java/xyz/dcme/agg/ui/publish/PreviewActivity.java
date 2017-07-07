package xyz.dcme.agg.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;

public class PreviewActivity extends BaseActivity {
    private static final String LOG_TAG = "PreviewActivity";
    private String mTitle;
    private String mContent;

    public static void startPreview(Context context, String title, String content) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(PreviewFragment.KEY_TITLE, title);
        intent.putExtra(PreviewFragment.KEY_CONTENT, content);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public void initView() {
        getExtraString();
        initToolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.preview_container, PreviewFragment.newInstance(mTitle, mContent))
                .commit();
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();
    }

    private void getExtraString() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(PreviewFragment.KEY_TITLE);
            mContent = intent.getStringExtra(PreviewFragment.KEY_CONTENT);
        }
    }
}
