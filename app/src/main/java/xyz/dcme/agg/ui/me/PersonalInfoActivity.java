package xyz.dcme.agg.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.ui.login.AccountInfo;
import xyz.dcme.agg.util.Constants;

public class PersonalInfoActivity extends BaseActivity {

    private Toolbar mToolbar;
    private String mUserName;
    private String mAvatar;
    private AccountInfo mAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mAccountInfo = intent.getParcelableExtra(Constants.EXTRA_ACCOUNT_INFO);

        }
        if (mAccountInfo != null) {
            mUserName = mAccountInfo.getUserName();
            mAvatar = mAccountInfo.getAvatarUrl();
        }
    }

    private void initViews() {
        mToolbar = getToolbar();
        mToolbar.setTitle(mUserName);
        mToolbar.inflateMenu(R.menu.menu_personal_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
