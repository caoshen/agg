package xyz.dcme.account;

import android.view.View;

public interface OnLoginClickListener{
    void onClick(View v);
    void onLogin(AccountInfo info);
    void onError(ErrorStatus status);
}
