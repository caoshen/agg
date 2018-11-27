package cn.okclouder.account;

import android.content.Context;
import android.view.View;

public abstract class OnCheckLoginClickListener implements View.OnClickListener {
    private final Context mContext;
    private final LoginHandler mHandler;

    public OnCheckLoginClickListener(Context context, LoginHandler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onClick(View v) {
        if (AccountManager.hasLoginAccount(mContext)) {
            doClick(v);
        } else {
            AccountManager.getAccount(mContext, mHandler);
        }
    }

    protected abstract void doClick(View v);

}
