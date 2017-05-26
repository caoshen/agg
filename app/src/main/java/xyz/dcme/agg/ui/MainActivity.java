package xyz.dcme.agg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import xyz.dcme.agg.util.ImageLoader;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private static final int REQUEST_SETTINGS = 200;
    private static final int REQUEST_LOGIN = 300;
    private NodeMainFragment mNodeMainFragment;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private TextView mUsername;
    private CircleImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (savedInstanceState == null) {
            mNodeMainFragment = NodeMainFragment.newInstance();
            transaction.add(R.id.main_content, mNodeMainFragment, NodeMainFragment.LOG_TAG);
        } else {
            mNodeMainFragment = (NodeMainFragment) fm.findFragmentByTag(NodeMainFragment.LOG_TAG);
        }

        transaction.commit();
        switchTo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void switchTo() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(mNodeMainFragment);
        transaction.commit();
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
            ImageLoader.loadAvatar(this, avatarUrl, mAvatar);
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
                switchTo();
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
                    Intent intent = new Intent(this, PersonalInfoActivity.class);
                    intent.putExtra(Constants.EXTRA_ACCOUNT_INFO, accountInfo);
                    startActivity(intent);
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
}
