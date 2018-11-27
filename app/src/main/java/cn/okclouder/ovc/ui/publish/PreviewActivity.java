package cn.okclouder.ovc.ui.publish;

import android.content.Context;
import android.content.Intent;

import cn.okclouder.ovc.R;
import cn.okclouder.library.base.BaseActivity;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.preview_container, PreviewFragment.newInstance(mTitle, mContent))
                .commit();
    }

    private void getExtraString() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(PreviewFragment.KEY_TITLE);
            mContent = intent.getStringExtra(PreviewFragment.KEY_CONTENT);
        }
    }
}
