package xyz.dcme.agg.frag.myself;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import xyz.dcme.account.AccountInfo;
import xyz.dcme.account.AccountManager;
import xyz.dcme.account.ErrorStatus;
import xyz.dcme.account.LoginConstants;
import xyz.dcme.account.LoginHandler;
import xyz.dcme.account.OnCheckLoginClickListener;
import xyz.dcme.agg.R;
import xyz.dcme.agg.frag.favourite.MyFavouriteArticleFragment;
import xyz.dcme.agg.frag.history.BrowserHistoryFragment;
import xyz.dcme.agg.frag.home.HomeControllerFragment;
import xyz.dcme.agg.frag.message.NotificationMessageFragment;
import xyz.dcme.agg.frag.settings.MySettingsFragment;
import xyz.dcme.agg.frag.user.UserHomePageActivity;
import xyz.dcme.library.util.ImageLoader;


public class MySelfFragment extends HomeControllerFragment {
    private static final String TAG = MySelfFragment.class.getSimpleName();
    private ImageView mUserImageView;
    private TextView mUserNameText;
    private LoginHandler mLoginHandler = new LoginHandler() {
        @Override
        public void onLogin(AccountInfo account) {
            updateAccountView(account);
        }

        @Override
        public void onError(ErrorStatus status) {

        }
    };
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    private void registerAccountBroadcast() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive, action:" + action);
                if (LoginConstants.ACTION_LOGIN_SUCCESS.equals(action)) {
                    updateAccountView(AccountManager.checkLocalLoginAccount(getActivity()));
                } else if (LoginConstants.ACTION_LOGOUT_ACCOUNT.equals(action)) {
                    initAccountView();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoginConstants.ACTION_LOGOUT_ACCOUNT);
        filter.addAction(LoginConstants.ACTION_LOGIN_SUCCESS);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, filter);
    }

    @Override
    protected View onCreateView() {
        Log.d(TAG, "onCreateView");
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_myself, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        QMUITopBar topbar = rootView.findViewById(R.id.topbar);
        topbar.setTitle(R.string.me);

        View itemAccount = rootView.findViewById(R.id.item_my_account);
        itemAccount.setOnClickListener(new OnCheckLoginClickListener(getActivity(), mLoginHandler) {
            @Override
            protected void doClick(View v) {
                AccountInfo accountInfo = AccountManager.checkLocalLoginAccount(getActivity());
                if (accountInfo != null) {
                    UserHomePageActivity.start(getActivity(), accountInfo.getUserName());
                }
            }
        });
        mUserImageView = itemAccount.findViewById(R.id.user_avatar);
        mUserNameText = itemAccount.findViewById(R.id.user_name);

        QMUIGroupListView groupList = rootView.findViewById(R.id.groupListView);
        QMUICommonListItemView itemMessage = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_notifications),
                getString(R.string.message), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView itemFavourite = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_star),
                getString(R.string.favourite), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView itemHistory = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_history),
                getString(R.string.history), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemMessage, new OnCheckLoginClickListener(getActivity(), mLoginHandler) {
                    @Override
                    protected void doClick(View v) {
                        startFragment(NotificationMessageFragment.newInstance());
                    }
                })
                .addItemView(itemFavourite, new OnCheckLoginClickListener(getActivity(), mLoginHandler) {
                    @Override
                    protected void doClick(View v) {
                        startFragment(MyFavouriteArticleFragment.newInstance(getActivity()));
                    }
                })
                .addItemView(itemHistory, new OnCheckLoginClickListener(getActivity(), mLoginHandler) {
                    @Override
                    protected void doClick(View v) {
                        startFragment(BrowserHistoryFragment.newInstance());
                    }
                }).addTo(groupList);

        QMUICommonListItemView itemSettings = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_settings),
                getString(R.string.settings), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemSettings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(new MySettingsFragment());
                    }
                }).addTo(groupList);

        AccountInfo info = AccountManager.checkLocalLoginAccount(getContext());
        if (info != null) {
            updateAccountView(info);
        }
    }

    private void doLogin() {
        AccountManager.getAccount(getActivity(), new LoginHandler() {
            @Override
            public void onLogin(AccountInfo account) {
                updateAccountView(account);
            }

            @Override
            public void onError(ErrorStatus status) {

            }
        });
    }

    private void updateAccountView(AccountInfo account) {
        Log.d(TAG, "updateAccountView");
        if (account != null) {
            ImageLoader.displayCircle(getActivity(), mUserImageView, account.getAvatarUrl());
            mUserNameText.setText(account.getUserName());
        }
    }

    private void initAccountView() {
        Log.d(TAG, "initAccountView");
        mUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_me));
        mUserNameText.setText(R.string.login_please);
    }

}
