package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.me.MeFragment;
import xyz.dcme.agg.ui.post.PostFragment;
import xyz.dcme.agg.util.Constants;

public class MainActivity extends BaseActivity {
    private BottomNavigationView mBottomNav;
    private PostFragment mPostFragment;
    private MeFragment mMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        int curFrag = R.id.action_home_page;

        if (savedInstanceState == null) {
            mPostFragment = PostFragment.newInstance();
            mMeFragment = MeFragment.newInstance();
            transaction.add(R.id.main_content, mPostFragment, PostFragment.TAG);
            transaction.add(R.id.main_content, mMeFragment, MeFragment.TAG);
        } else {
            mPostFragment = (PostFragment) fm.findFragmentByTag(PostFragment.TAG);
            mMeFragment = (MeFragment) fm.findFragmentByTag(MeFragment.TAG);
            curFrag = savedInstanceState.getInt(Constants.CUR_FRAG, 0);
        }

        transaction.commit();
        switchTo(curFrag);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CUR_FRAG, mBottomNav.getSelectedItemId());
    }

    private void switchTo(int curFrag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (curFrag) {
            case R.id.action_home_page: {
                transaction.show(mPostFragment).hide(mMeFragment);
                break;
            }
            case R.id.action_me: {
                transaction.show(mMeFragment).hide(mPostFragment);
                break;
            }
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initNavBar();
    }

    private void initNavBar() {
        mBottomNav = (BottomNavigationView) findViewById(R.id.nav_bar);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchTo(item.getItemId());
                return true;
            }
        });
    }
}
