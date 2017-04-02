package xyz.dcme.agg.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.login.AccountInfo;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.ui.personal.PersonalInfoActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.AnimationUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class MeFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final long ALPHA_ANIMATIONS_DURATION = 200;
    private static final int REQUEST_LOGIN = 100;
    private static final String TAG = LogUtils.makeLogTag("MeFragment");

    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private RelativeLayout mTitleContainer;
    private TextView mTitle;
    private RelativeLayout mAccount;
    private CircleImageView mAvatar;
    private TextView mUsername;

    private boolean isTitleVisible = false;
    private boolean isTitleContainerVisible = true;
    private AccountInfo mAccountInfo;
    private boolean mIsLogin = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        initViews(root);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_me, menu);
    }

    private void initViews(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
        }

        mAccount = (RelativeLayout) view.findViewById(R.id.account);
        mAccount.setOnClickListener(this);

        mAvatar = (CircleImageView) view.findViewById(R.id.avatar);
        mUsername = (TextView) view.findViewById(R.id.user_name);

        mIsLogin = AccountUtils.hasActiveAccount(getActivity());
        if (mIsLogin) {
            mAccountInfo = AccountUtils.getActiveAccountInfo(getActivity());
            loadAccountInfo(mAccountInfo);
        }
//        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);
//        mTitleContainer = (RelativeLayout) view.findViewById(R.id.main_title);
//        mTitle = (TextView) view.findViewById(R.id.toolbar_title);
//
//        mToolbar.inflateMenu(R.menu.menu_me);
//        mAppBar.addOnOffsetChangedListener(this);
//        AnimationUtils.startAlphaAnimation(mToolbar, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float offsetPercent = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(offsetPercent);
        handleTitleVisibility(offsetPercent);
    }

    private void handleTitleVisibility(float offsetPercent) {
        if (offsetPercent >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            showTitle();
        } else {
            hideTitle();
        }
    }

    private void showTitle() {
        if (!isTitleVisible) {
            AnimationUtils.startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            isTitleVisible = true;
        }
    }

    private void hideTitle() {
        if (isTitleVisible) {
            AnimationUtils.startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTitleVisible = false;
        }
    }

    private void handleAlphaOnTitle(float offsetPercent) {
        if (offsetPercent >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            hideTitleDetails();
        } else {
            showTitleDetails();
        }
    }

    private void hideTitleDetails() {
        if (isTitleContainerVisible) {
            AnimationUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTitleContainerVisible = false;
        }
    }

    private void showTitleDetails() {
        if (!isTitleContainerVisible) {
            AnimationUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            isTitleContainerVisible = true;
        }
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
        }
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
}
