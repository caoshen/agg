package xyz.dcme.agg.frag.favourite;


import android.content.Context;

import xyz.dcme.account.AccountInfo;
import xyz.dcme.account.AccountManager;

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
