package xyz.dcme.agg.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.topic.AppManager;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        setContentView(getLayoutId());
        mContext = this;
        getIntentData();
        initView();
    }

    private void doBeforeSetContentView() {
        AppManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void getIntentData() {

    }

    public abstract int getLayoutId();

    public abstract void initView();

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }
}
