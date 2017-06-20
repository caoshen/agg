package xyz.dcme.agg.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;

public class TopicActivity extends BaseActivity {
    private static final String EXTRA_USER_NAME = "extra_username";
    private String mUserName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra(EXTRA_USER_NAME);
            initFragment();
        }
    }

    private void initFragment() {
        Fragment fragment = TopicFragment.newInstance(mUserName);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.topic_container, fragment).commit();
    }

    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        context.startActivity(intent);
    }
}
