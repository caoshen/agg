package xyz.dcme.agg.frag.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragmentActivity;


public class UserHomePageActivity extends BaseFragmentActivity {
    @Override
    protected int getContextViewId() {
        return R.id.id_user_home_page_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = UserHomePageFragment.newInstance();
        String tag = UserHomePageFragment.TAG;
        getSupportFragmentManager().beginTransaction()
                .add(getContextViewId(), fragment, tag)
                .addToBackStack(tag)
                .commit();
    }
}
