package cn.okclouder.ovc.ui.about;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import cn.okclouder.library.base.BaseActivity;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.util.VersionUtil;

public class AboutActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        initToolbar();

        TextView version = (TextView) findViewById(R.id.app_version_name);
        String versionName = VersionUtil.getAppVersionName(this);
        if (!TextUtils.isEmpty(versionName)) {
            versionName = "V" + versionName;
        }
        version.setText(getString(R.string.app_version_name, versionName));
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.about);
        }
    }
}
