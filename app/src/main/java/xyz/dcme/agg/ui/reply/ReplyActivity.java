package xyz.dcme.agg.ui.reply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;

public class ReplyActivity extends BaseActivity {
    private static final String EXTRA_USER_NAME = "extra_username";
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        initData();
        initFragment();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra(EXTRA_USER_NAME);
        }
    }

    private void initFragment() {
        Fragment fragment = ReplyFragment.newInstance(mUserName);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.reply_container, fragment).commit();
    }

    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, ReplyActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        context.startActivity(intent);
    }
}
