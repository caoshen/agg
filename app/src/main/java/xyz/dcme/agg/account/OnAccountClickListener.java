package xyz.dcme.agg.account;

import android.content.Context;
import android.view.View;

import xyz.dcme.agg.ui.personal.PersonalInfoActivity;

public class OnAccountClickListener implements View.OnClickListener {
    private Context mContext;
    private AccountInfo mInfo;

    public OnAccountClickListener(Context context, AccountInfo info) {
        mInfo = info;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        PersonalInfoActivity.start(mContext, mInfo);
    }
}
