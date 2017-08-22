package xyz.dcme.agg.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
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
import xyz.dcme.library.base.BaseActivity;
import xyz.dcme.agg.ui.personal.page.PersonalInfoContract;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.util.LogUtils;

public class PersonalInfoActivity extends BaseActivity implements PersonalInfoContract.View {

    private static final String LOG_TAG = "PersonalInfoActivity";
    private Toolbar mToolbar;
    private String mUserName;
    private CircleImageView mImage;
    private ViewPager mPager;
    private TabLayout mTab;
    private AppBarLayout mAppBarLayout;
    private PersonalInfoContract.Presenter mPresenter;

    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        intent.putExtra(Constants.EXTRA_ACCOUNT_NAME, userName);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void getData() {
        super.getData();
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = getUserName(intent);
        }
    }

    @Override
    public void initView() {
        initViews();
        mPresenter = new PersonalInfoPresenter(this);
        mPresenter.loadDetail(mUserName);
    }

    private String getUserName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            List<String> params = data.getPathSegments();
            if (params != null && params.size() > 1) {
                return params.get(1);
            }
        } else {
            return intent.getStringExtra(Constants.EXTRA_ACCOUNT_NAME);
        }
        return null;
    }

    private void initViews() {
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(PersonalInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(this).load(imageUrl).into(mImage);
        }
    }
}
