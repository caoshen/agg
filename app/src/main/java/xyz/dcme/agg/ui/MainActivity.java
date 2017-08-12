package xyz.dcme.agg.ui;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.node.NodeMainFragment;
import xyz.dcme.agg.ui.personal.PersonalInfoActivity;
import xyz.dcme.agg.ui.settings.SettingsActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.library.util.ImageLoader;
import xyz.dcme.library.util.LogUtils;
import xyz.dcme.library.base.BaseActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private static final int REQUEST_SETTINGS = 200;
    private static final int REQUEST_LOGIN = 300;
    private static final String TAG = "MainActivity";
    private NodeMainFragment mNodeMainFragment;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private TextView mUsername;
    private CircleImageView mAvatar;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        mNodeMainFragment = (NodeMainFragment) fm.findFragmentByTag(NodeMainFragment.LOG_TAG);
        if (mNodeMainFragment == null) {
            mNodeMainFragment = NodeMainFragment.newInstance();
        }
        transaction.replace(R.id.main_content, mNodeMainFragment, NodeMainFragment.LOG_TAG);
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
        mAvatar = (CircleImageView) navHeader.findViewById(R.id.nav_avatar);
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
            ImageLoader.display(this, mAvatar, avatarUrl);
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
            case R.id.action_node: {
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
