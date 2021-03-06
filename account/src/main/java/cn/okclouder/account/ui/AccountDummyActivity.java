package cn.okclouder.account.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.ErrorStatus;
import cn.okclouder.account.LoginConstants;
import cn.okclouder.account.LoginHandler;


public class AccountDummyActivity extends Activity {
    private static final int REQ_CODE_LOGIN = 1;
    private static LoginHandler mCallback;

    public static void startLogin(Context context, LoginHandler handler) {
        mCallback = handler;
        Intent intent = new Intent(context, AccountDummyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQ_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();

        if (mCallback == null) {
            return;
        }
        if (requestCode == REQ_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                AccountInfo accountInfo = data.getParcelableExtra(LoginConstants.KEY_EXTRA_LOGIN_ACCOUNT);
                mCallback.onLogin(accountInfo);
            } else {
                ErrorStatus errorStatus = new ErrorStatus(ErrorStatus.LOGIN_CANCEL,
                        ErrorStatus.LOGIN_CANCEL_REASON);
                mCallback.onError(errorStatus);
            }
        }
        mCallback = null;
    }
}
