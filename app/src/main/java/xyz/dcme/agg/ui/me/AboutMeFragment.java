package xyz.dcme.agg.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.login.AccountInfo;
import xyz.dcme.agg.ui.login.LoginActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.transformation.CircleTransformation;


public class AboutMeFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_ABOUT_ME = 10;
    private LinearLayout mAboutMe;
    private AccountInfo mAccountInfo;
    private TextView mIdText;
    private ImageView mAvatar;
    private AccountHelper mHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_about_me, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        checkLogin();
    }

    private void checkLogin() {
        mHelper = new AccountHelper(getActivity());
        if (mHelper.isLogin()) {
            loadAccountInfo(mHelper.getAccountInfo());
        }
    }

    private void initViews(View view) {
        mAboutMe = (LinearLayout) view.findViewById(R.id.about_me);
        mAboutMe.setOnClickListener(this);
        mIdText = (TextView) view.findViewById(R.id.id_text);
        mAvatar = (ImageView) view.findViewById(R.id.about_me_avatar);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_me : {
                if (AccountUtils.hasActiveAccount(getActivity())) {
                    PersonalInfoActivity.showPersonalInfo(getActivity());
                } else {
                    LoginActivity.startLoginProcess(getActivity(), REQUEST_ABOUT_ME);
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ABOUT_ME) {
            if (data != null) {
                mAccountInfo = data.getParcelableExtra(LoginActivity.KEY_EXTRA_LOGIN_ACCOUNT);
                loadAccountInfo(mAccountInfo);
                AccountUtils.setActiveAccount(getActivity(), mAccountInfo.getNickName());
            }
        }
    }

    private void loadAccountInfo(AccountInfo accountInfo) {
        if (accountInfo != null) {
            String id = accountInfo.getId();
            if (!TextUtils.isEmpty(id)) {
                mIdText.setText(id);
            }
            String avatarUrl = accountInfo.getAvatarUrl();
            if (!TextUtils.isEmpty(avatarUrl)) {
                Glide.with(getActivity())
                        .load(avatarUrl)
                        .placeholder(R.drawable.ic_default_avatar)
                        .transform(new CircleTransformation(getActivity()))
                        .into(mAvatar);
            }
        }
    }
}
