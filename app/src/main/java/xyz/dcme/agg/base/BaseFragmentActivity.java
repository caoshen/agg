package xyz.dcme.agg.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;


public abstract class BaseFragmentActivity extends AppCompatActivity {

    private FrameLayout mFragmentContainer;

    public abstract int getContextViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        mFragmentContainer = new FrameLayout(this);
        mFragmentContainer.setId(getContextViewId());
        setContentView(mFragmentContainer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            popBackStack();
        }
    }

    private void popBackStack() {
        // TODO
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getContextViewId());
    }
}
