package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private PostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragment();
    }

    private void initViews() {
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        PostFragment postFragment = (PostFragment) fm.findFragmentById(R.id.main_content);
        if (postFragment == null) {
            postFragment = PostFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, postFragment, R.id.main_content);
        }
        mPresenter = new PostPresenter(postFragment);
    }
}
