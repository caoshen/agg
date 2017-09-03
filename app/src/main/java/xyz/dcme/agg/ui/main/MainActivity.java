package xyz.dcme.agg.ui.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.service.NotificationService;
import xyz.dcme.agg.ui.favorite.FavoriteFragment;
import xyz.dcme.agg.ui.history.HistoryFragment;
import xyz.dcme.agg.ui.hot.HotFragment;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.news.NewsMainFragment;
import xyz.dcme.agg.ui.node.NodeMainFragment;
import xyz.dcme.agg.ui.notify.MessageFragment;
import xyz.dcme.agg.ui.personal.PersonalInfoActivity;
import xyz.dcme.agg.ui.settings.SettingsActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.base.BaseActivity;
import xyz.dcme.library.util.ImageLoader;
import xyz.dcme.library.util.LogUtils;
import xyz.dcme.library.util.StatusBarUtil;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private static final int REQUEST_SETTINGS = 200;
    private static final int REQUEST_LOGIN = 300;
    private static final String TAG = "MainActivity";
    private NodeMainFragment mNodeMainFragment;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private TextView mUsername;
    private ImageView mAvatar;
    private BroadcastReceiver mReceiver;
    private NewsMainFragment mNewsMainFragment;
    private HotFragment mHotFragment;
    private FavoriteFragment mFavFragment;
    private MessageFragment mMessageFragment;
    private HistoryFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        initFragment();
        registerBroadcast();
        startNotify();
    }

    private void setStatusBar() {
        StatusBarUtil.setTranslucentDrawer(this, mDrawer);
    }

    private void startNotify() {
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    private void initFragment() {
        switchFragment(R.id.action_front);
    }

    private void switchFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (id == R.id.action_front) {
            mNewsMainFragment = (NewsMainFragment) fm.findFragmentByTag(NewsMainFragment.LOG_TAG);
            if (mNewsMainFragment == null) {
                mNewsMainFragment = new NewsMainFragment();
            }
            transaction.replace(R.id.main_content, mNewsMainFragment, NewsMainFragment.LOG_TAG);
        } else if (id == R.id.action_message) {
            mMessageFragment = (MessageFragment) fm.findFragmentByTag(MessageFragment.LOG_TAG);
            if (mMessageFragment == null) {
                mMessageFragment = MessageFragment.newInstance();
            }
            transaction.replace(R.id.main_content, mMessageFragment, MessageFragment.LOG_TAG);
        } else if (id == R.id.action_hot) {
            mHotFragment = (HotFragment) fm.findFragmentByTag(HotFragment.LOG_TAG);
            if (mHotFragment == null) {
                mHotFragment = HotFragment.newInstance();
            }
            transaction.replace(R.id.main_content, mHotFragment, HotFragment.LOG_TAG);
        } else if (id == R.id.action_explore) {
            mNodeMainFragment = (NodeMainFragment) fm.findFragmentByTag(NodeMainFragment.LOG_TAG);
            if (mNodeMainFragment == null) {
                mNodeMainFragment = NodeMainFragment.newInstance();
            }
            transaction.replace(R.id.main_content, mNodeMainFragment, NodeMainFragment.LOG_TAG);
        } else if (id == R.id.action_fav) {
            mFavFragment = (FavoriteFragment) fm.findFragmentByTag(FavoriteFragment.LOG_TAG);
            if (mFavFragment == null) {
                mFavFragment = FavoriteFragment.newInstance(AccountUtils.getUserName(this), true);
            }
            transaction.replace(R.id.main_content, mFavFragment, FavoriteFragment.LOG_TAG);
        } else if (id == R.id.action_history) {
            mHistoryFragment = (HistoryFragment) fm.findFragmentByTag(HistoryFragment.LOG_TAG);
            if (mHistoryFragment == null) {
                mHistoryFragment = HistoryFragment.newInstance();
            }
            transaction.replace(R.id.main_content, mHistoryFragment, HistoryFragment.LOG_TAG);
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initDrawer();
        initNav();
        initAccount();
    }

    private void initAccount() {
        View navHeader = mNavigationView.getHeaderView(0);
        mAvatar = (ImageView) navHeader.findViewById(R.id.nav_avatar);
        mUsername = (TextView) navHeader.findViewById(R.id.nav_user_name);
        mAvatar.setOnClickListener(this);
        mUsername.setOnClickListener(this);

        updateAccountInfo();
    }

    private void updateAccountInfo() {
        boolean hasLogin = AccountUtils.hasActiveAccount(this);
        if (hasLogin) {
            AccountInfo accountInfo = AccountUtils.getActiveAccountInfo(this);
            String name = accountInfo.getUserName();
            String avatarUrl = accountInfo.getAvatarUrl();
            mUsername.setText(name);
            ImageLoader.displayCircle(this, mAvatar, avatarUrl);
        } else {
            mUsername.setText(R.string.login_please);
            mAvatar.setImageResource(R.drawable.ic_me);
        }
    }

    private void initDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initNav() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_main);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_SETTINGS);
                break;
            }
            case R.id.action_history: {
                switchFragment(R.id.action_history);
                break;
            }
            case R.id.action_fav: {
                switchFragment(R.id.action_fav);
                break;
            }
            case R.id.action_explore: {
                switchFragment(R.id.action_explore);
                break;
            }
            case R.id.action_hot: {
                switchFragment(R.id.action_hot);
                break;
            }
            case R.id.action_message: {
                switchFragment(R.id.action_message);
                break;
            }
            case R.id.action_front: {
                switchFragment(R.id.action_front);
                break;
            }
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_avatar:
            case R.id.nav_user_name: {
                boolean hasActiveAccount = AccountUtils.hasActiveAccount(this);
                if (hasActiveAccount) {
                    AccountInfo accountInfo = AccountUtils.getActiveAccountInfo(this);
                    PersonalInfoActivity.start(this, accountInfo.getUserName());
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                mDrawer.closeDrawer(GravityCompat.START);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LOGIN || requestCode == REQUEST_SETTINGS) {
                if (data != null) {
                    AccountInfo info = data.getParcelableExtra(LoginActivity.KEY_EXTRA_LOGIN_ACCOUNT);
                    AccountUtils.setActiveAccountInfo(this, info);
                }
                updateAccountInfo();
            }
        }
    }

    public void registerBroadcast() {
        if (mReceiver == null) {
            mReceiver = new MainReceiver();
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ACTION_LOGIN_SUCCESS);
            lbm.registerReceiver(mReceiver, filter);
        }
    }

    public void unRegisterBroadcast() {
        if (mReceiver != null) {
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            lbm.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    public void toggleDrawer() {
        if (mDrawer == null) {
            return;
        }
        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else {
            mDrawer.openDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && (mDrawer.isDrawerOpen(Gravity.START))) {
            mDrawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            LogUtils.d(TAG, "onReceive -> action: " + action);
            if (Constants.ACTION_LOGIN_SUCCESS.equals(action)) {
                updateAccountInfo();
            }
        }
    }
}
