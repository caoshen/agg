package xyz.dcme.agg.ui.postdetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.ActivityUtils;


public class PostDetailActivity extends AppCompatActivity {

    private String mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_post_detail);
        initData();
        initFragment();
    }

    private void initData() {
        Intent intent = getIntent();
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
}
