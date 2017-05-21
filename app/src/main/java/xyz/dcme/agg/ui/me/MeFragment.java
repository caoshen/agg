package xyz.dcme.agg.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.ui.favorite.FavoriteActivity;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.personal.PersonalInfoActivity;
import xyz.dcme.agg.ui.reply.ReplyActivity;
import xyz.dcme.agg.ui.settings.SettingsActivity;
import xyz.dcme.agg.ui.topic.TopicActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class MeFragment extends Fragment implements
        View.OnClickListener, Toolbar.OnMenuItemClickListener {
    public static final String TAG = "MeFragment";

    private static final int REQUEST_LOGIN = 100;
    private static final int REQUEST_SETTINGS = 200;

    private Toolbar mToolbar;
    private RelativeLayout mAccount;
    private CircleImageView mAvatar;
    private TextView mUsername;

    private AccountInfo mAccountInfo;
    private boolean mIsLogin = false;

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        initViews(root);

        return root;
    }

    private void initViews(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
            mToolbar.inflateMenu(R.menu.menu_me);
            mToolbar.setOnMenuItemClickListener(this);
        }

        mAccount = (RelativeLayout) view.findViewById(R.id.account);
        mAccount.setOnClickListener(this);

        mAvatar = (CircleImageView) view.findViewById(R.id.avatar);
        mUsername = (TextView) view.findViewById(R.id.user_name);

        updateAccount();

        TextView myTopic = (TextView) view.findViewById(R.id.my_topic);
        TextView myReply = (TextView) view.findViewById(R.id.my_reply);
        TextView myFocus = (TextView) view.findViewById(R.id.my_focus);
        TextView myHistory = (TextView) view.findViewById(R.id.my_history);

        myTopic.setOnClickListener(this);
        myReply.setOnClickListener(this);
        myFocus.setOnClickListener(this);
        myHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account: {
                if (mIsLogin) {
                    Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                    intent.putExtra(Constants.EXTRA_ACCOUNT_INFO, mAccountInfo);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
            }
            case R.id.my_topic: {
                if (mIsLogin) {
                    TopicActivity.start(getActivity(), mAccountInfo.getUserName());
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
            }
            case R.id.my_reply: {
                if (mIsLogin) {
                    ReplyActivity.start(getActivity(), mAccountInfo.getUserName());
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
            }
            case R.id.my_focus: {
                if (mIsLogin) {
                    FavoriteActivity.start(getActivity(), mAccountInfo.getUserName());
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_LOGIN) {
            if (data != null) {
                mAccountInfo = data.getParcelableExtra(LoginActivity.KEY_EXTRA_LOGIN_ACCOUNT);
                AccountUtils.setActiveAccountInfo(getActivity(), mAccountInfo);
                mIsLogin = true;
                loadAccountInfo(mAccountInfo);
            } else {
                LogUtils.LOGD(TAG, "data is null!");
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SETTINGS) {
            updateAccount();
        }
    }

    private void updateAccount() {
        mIsLogin = AccountUtils.hasActiveAccount(getActivity());
        if (mIsLogin) {
            mAccountInfo = AccountUtils.getActiveAccountInfo(getActivity());
            loadAccountInfo(mAccountInfo);
        } else {
            mAccountInfo = null;
            clearAccountInfo();
        }
    }

    private void clearAccountInfo() {
        mUsername.setText(R.string.login_please);
        mAvatar.setImageResource(R.drawable.ic_me);
    }

    private void loadAccountInfo(AccountInfo accountInfo) {
        if (accountInfo != null) {
            String name = accountInfo.getUserName();
            if (!TextUtils.isEmpty(name)) {
                mUsername.setText(name);
            }
            String avatarUrl = accountInfo.getAvatarUrl();
            if (!TextUtils.isEmpty(avatarUrl)) {
                Glide.with(getActivity())
                        .load(avatarUrl)
                        .into(mAvatar);
            }
            LogUtils.LOGD(TAG, "username: " + name + " avatar: " + avatarUrl);
        } else {
            LogUtils.LOGD(TAG, "account info is null!");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_settings) {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivityForResult(intent, REQUEST_SETTINGS);
            }
        }
        return true;
    }

}
