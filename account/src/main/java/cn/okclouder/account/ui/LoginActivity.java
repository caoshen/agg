package cn.okclouder.account.ui;

import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import cn.okclouder.account.R;
import cn.okclouder.arch.QMUIFragmentActivity;

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

