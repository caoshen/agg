package xyz.dcme.agg.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.ui.login.AccountInfo;
import xyz.dcme.agg.util.Constants;

public class PersonalInfoActivity extends BaseActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String mUserName;
    private String mAvatar;
    private AccountInfo mAccountInfo;
    private CircleImageView mImage;
    private ViewPager mPager;

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
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        mToolbar = getToolbar();
        ActionBar ab = getSupportActionBar();
        if (!TextUtils.isEmpty(mUserName) && ab != null) {
            ab.setTitle(mUserName);
        }

        mImage = (CircleImageView) findViewById(R.id.avatar);
        if (!TextUtils.isEmpty(mAvatar)) {
            Glide.with(this).load(mAvatar).into(mImage);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        PersonalPagerAdapter adapter = new PersonalPagerAdapter(this, fm);
        mPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_info, menu);
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
