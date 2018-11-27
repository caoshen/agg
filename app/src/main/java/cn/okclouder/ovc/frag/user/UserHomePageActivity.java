package cn.okclouder.ovc.frag.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;


public class UserHomePageActivity extends BaseFragmentActivity {
    private static final String KEY_USER_NAME = "user_name";

    @Override
    protected int getContextViewId() {
        return R.id.id_user_home_page_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QMUIStatusBarHelper.setStatusBarLightMode(this);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra(KEY_USER_NAME);

            Fragment fragment = UserHomePageFragment.newInstance(name);
            String tag = UserHomePageFragment.TAG;
            getSupportFragmentManager().beginTransaction()
                    .add(getContextViewId(), fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, UserHomePageActivity.class);
        intent.putExtra(KEY_USER_NAME, userName);
        context.startActivity(intent);
    }
}
