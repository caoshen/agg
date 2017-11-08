package xyz.dcme.agg.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.AnimationUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.base.BaseActivity;
import xyz.dcme.library.util.LogUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = LogUtils.makeLogTag("LoginActivity");
    public static final String KEY_EXTRA_LOGIN_ACCOUNT = "key_login_account";

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.REGISTER_URL));
                startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            startLogin(email, password);
        }
    }

    private void startLogin(final String email, final String password) {
        HttpUtils.get(Constants.LOGIN_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showProgress(false);
            }

            @Override
            public void onResponse(String response, int id) {
                login(response, email, password);         
            }
        });
    }

    private void login(String response, String email, String password) {
        Map<String, String> params = getLoginParam(response, email, password);
        if (params == null) {
            return;
        }

        HttpUtils.post(Constants.LOGIN_URL, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.d(TAG, e.toString());
                showProgress(false);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(TAG, response);
                showProgress(false);
                onLoginFinish();
            }
        });
    }

    private void onLoginFinish() {
        HttpUtils.get(Constants.HOME_URL + "/setting", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                AccountInfo accountInfo = AccountParser.parseResponse(response);
                String username = accountInfo.getUserName();

                if (!TextUtils.isEmpty(username)) {
                    AccountUtils.setActiveAccountInfo(LoginActivity.this, accountInfo);
                    sendLoginBroadcast();
                    finish();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        });
    }

    private void sendLoginBroadcast() {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(Constants.ACTION_LOGIN_SUCCESS);
        lbm.sendBroadcast(intent);
    }

    @Nullable
    private Map<String, String> getLoginParam(String response, String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        String xsrf = HttpUtils.findXsrf(response);
        if (!TextUtils.isEmpty(xsrf)) {
            params.put("_xsrf", xsrf);
        }

        LogUtils.d(TAG, "getLoginParam -> email: " + email + " password: " + password
            + " _xsrf: " + xsrf);

        return params;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        AnimationUtils.showProgress(mProgressView, mLoginFormView, show);
    }

}

