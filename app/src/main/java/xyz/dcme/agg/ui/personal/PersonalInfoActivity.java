package xyz.dcme.agg.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class PersonalInfoActivity extends BaseActivity {

    private static final String LOG_TAG = "PersonalInfoActivity";
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String mUserName;
    private String mAvatar;
    private AccountInfo mAccountInfo;
    private CircleImageView mImage;
    private ViewPager mPager;
    private TabLayout mTab;
    private AppBarLayout mAppBarLayout;

    public static void start(Context context, AccountInfo info) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        intent.putExtra(Constants.EXTRA_ACCOUNT_INFO, info);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        initViews();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView() {

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mAccountInfo = intent.getParcelableExtra(Constants.EXTRA_ACCOUNT_INFO);
            Uri data = intent.getData();
            List<String> params = data.getPathSegments();
            mUserName = params.get(1);
        }
        if (mAccountInfo != null) {
            mUserName = mAccountInfo.getUserName();
            mAvatar = mAccountInfo.getAvatarUrl();
        }
    }

    private void initViews() {

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!TextUtils.isEmpty(mUserName)) {
            mToolbar.setTitle(mUserName);
        }

        mImage = (CircleImageView) findViewById(R.id.avatar);
        if (!TextUtils.isEmpty(mAvatar)) {
            Glide.with(this).load(mAvatar).into(mImage);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        PersonalPagerAdapter adapter = new PersonalPagerAdapter(this, fm, mUserName);
        mPager.setAdapter(adapter);
        mTab = (TabLayout) findViewById(R.id.info_tabs);
        mTab.setupWithViewPager(mPager);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_info);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                float ratio = 1 - Math.abs(verticalOffset * 1.0F / (scrollRange));
                mImage.setAlpha(ratio);
                LogUtils.d(LOG_TAG, "initViews -> onOffsetChanged -> ratio: " + ratio);
            }
        });
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
