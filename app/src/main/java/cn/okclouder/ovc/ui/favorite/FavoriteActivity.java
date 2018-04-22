package cn.okclouder.ovc.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import cn.okclouder.ovc.R;
import cn.okclouder.library.base.BaseActivity;

public class FavoriteActivity extends BaseActivity {
    private static final String EXTRA_USER_NAME = "extra_username";
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_favorite;
    }

    @Override
    public void initView() {

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra(EXTRA_USER_NAME);
        }
    }

    private void initFragment() {
        Fragment fragment = FavoriteFragment.newInstance(mUserName, false);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fav_container, fragment).commit();
    }

    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, FavoriteActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        context.startActivity(intent);
    }
}