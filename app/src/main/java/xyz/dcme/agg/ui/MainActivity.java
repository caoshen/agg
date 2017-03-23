package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.login.LoginFragment;
import xyz.dcme.agg.ui.me.AboutMeFragment;
import xyz.dcme.agg.ui.post.PostFragment;
import xyz.dcme.agg.ui.post.PostPresenter;
import xyz.dcme.agg.util.ActivityUtils;

public class MainActivity extends BaseActivity {

    private PostPresenter mPresenter;
    private BottomNavigationView mBottomNav;
    private PostFragment mPostFragment;
    private LoginFragment mLoginFragment;
    private AboutMeFragment mAboutMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragment();
    }

    private void initViews() {
        getToolbar();

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home_page: {
                        initFragment();
                        if (mPostFragment != null && mPostFragment.isHidden()) {
                            FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction().show(mPostFragment).commit();
                        }
                        if (mAboutMeFragment != null && !mAboutMeFragment.isHidden()) {
                            FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction().hide(mAboutMeFragment).commit();
                        }
                        break;
                    }
                    case R.id.action_search: {
                        break;
                    }
                    case R.id.action_me: {
                        initLoginFragment();
                        if (mPostFragment != null && !mPostFragment.isHidden()) {
                            FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction().hide(mPostFragment).commit();
                        }
                        if (mAboutMeFragment != null && mAboutMeFragment.isHidden()) {
                            FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction().show(mAboutMeFragment).commit();
                        }
                        break;
                    }
                    default: {
                        return true;
                    }
                }
                return true;
            }
        });
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_content);
        if (fragment instanceof PostFragment) {
            mPostFragment = (PostFragment) fragment;
        }
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, mPostFragment, R.id.main_content);
        }
        mPresenter = new PostPresenter(mPostFragment);
    }

    private void initLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_content);
        if (fragment instanceof AboutMeFragment) {
            mAboutMeFragment = (AboutMeFragment) fragment;
        }
        if (mAboutMeFragment == null) {
            mAboutMeFragment = new AboutMeFragment();
            ActivityUtils.addFragmentToActivity(fm, mAboutMeFragment, R.id.main_content);
        }
    }
}
