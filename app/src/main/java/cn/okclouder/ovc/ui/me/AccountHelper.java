package cn.okclouder.ovc.ui.me;

import android.content.Context;
import android.text.TextUtils;

import cn.okclouder.ovc.account.AccountInfo;
import cn.okclouder.ovc.util.SharedPrefUtils;

public class AccountHelper {

    private AccountInfo mAccountInfo;

    public AccountHelper(Context context) {
        String userName = SharedPrefUtils.getUserName(context);
        String avatar = SharedPrefUtils.getAvatar(context);
        mAccountInfo = new AccountInfo();
        mAccountInfo.setId(userName);
        mAccountInfo.setAvatarUrl(avatar);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(mAccountInfo.getId());
    }

    public AccountInfo getAccountInfo() {
        return mAccountInfo;
    }

    public static void saveAccountInfo(Context context, AccountInfo accountInfo) {
        SharedPrefUtils.setUserName(context, accountInfo.getId());
        SharedPrefUtils.setAvatar(context, accountInfo.getAvatarUrl());
    }
}
