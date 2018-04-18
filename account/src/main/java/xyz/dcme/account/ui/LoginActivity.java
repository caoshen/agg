package xyz.dcme.account.ui;

import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import xyz.dcme.account.R;
import xyz.dcme.arch.QMUIFragmentActivity;

public class LoginActivity extends QMUIFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.login_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QMUIStatusBarHelper.setStatusBarLightMode(this);

        if (null == savedInstanceState) {
            LoginAccountFragment fragment = new LoginAccountFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(getContextViewId(), fragment, LoginAccountFragment.class.getSimpleName())
                    .commit();
        }
    }
}

