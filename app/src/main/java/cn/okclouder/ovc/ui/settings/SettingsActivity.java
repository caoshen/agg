package cn.okclouder.ovc.ui.settings;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import cn.okclouder.ovc.R;
import cn.okclouder.library.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.settings);
        }
    }
}
