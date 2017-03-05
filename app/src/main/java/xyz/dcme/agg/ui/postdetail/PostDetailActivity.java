package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.ActivityUtils;


public class PostDetailActivity extends AppCompatActivity {

    private static final String KEY_POST_DETAIL_URL = "key_post_detail_url";
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initData();
        initFragment();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(KEY_POST_DETAIL_URL);
        }
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        PostDetailFragment postFragment = (PostDetailFragment) fm.findFragmentById(R.id.post_detail_main);
        if (postFragment == null) {
            postFragment = PostDetailFragment.newInstance(mUrl);
            ActivityUtils.addFragmentToActivity(fm, postFragment, R.id.post_detail_main);
        }
        new PostDetailPresenter(postFragment);
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(KEY_POST_DETAIL_URL, url);
        context.startActivity(intent);
    }
}
