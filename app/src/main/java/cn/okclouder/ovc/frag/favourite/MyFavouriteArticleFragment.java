package cn.okclouder.ovc.frag.favourite;


import android.content.Context;

import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.AccountManager;

public class MyFavouriteArticleFragment extends FavouriteArticleFragment {

    public static FavouriteArticleFragment newInstance(Context context) {
        AccountInfo accountInfo = AccountManager.checkLocalLoginAccount(context);
        if (accountInfo != null) {
            return FavouriteArticleFragment.newInstance(accountInfo.getUserName());
        } else {
            return FavouriteArticleFragment.newInstance("");
        }
    }
}
