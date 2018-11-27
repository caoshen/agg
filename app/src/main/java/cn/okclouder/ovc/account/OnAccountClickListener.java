package cn.okclouder.ovc.account;

import android.content.Context;
import android.view.View;

import cn.okclouder.ovc.frag.user.UserHomePageActivity;

public class OnAccountClickListener implements View.OnClickListener {
    private Context mContext;
    private String mUserName;

    public OnAccountClickListener(Context context, String userName) {
        mUserName = userName;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        UserHomePageActivity.start(mContext, mUserName);
    }
}
