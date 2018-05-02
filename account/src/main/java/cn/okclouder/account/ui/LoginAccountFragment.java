package cn.okclouder.account.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import cn.okclouder.account.AccountInfo;
import cn.okclouder.account.AccountManager;
import cn.okclouder.account.AccountParser;
import cn.okclouder.account.LoginConstants;
import cn.okclouder.account.R;
import cn.okclouder.arch.QMUIFragment;
import cn.okclouder.library.util.HttpUtils;


public class LoginAccountFragment extends QMUIFragment {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_account, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        QMUITopBar topBar = rootView.findViewById(R.id.topbar);
        topBar.setTitle(R.string.login);
        topBar.addLeftImageButton(R.drawable.ic_topbar_back_blue, com.qmuiteam.qmui.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });

        mEmailView = (AutoCompleteTextView) rootView.findViewById(R.id.email);

        mPasswordView = (EditText) rootView.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                if (id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) rootView.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = rootView.findViewById(R.id.login_form);

        mProgressView = rootView.findViewById(R.id.login_progress);

        TextView register = (TextView) rootView.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(LoginConstants.REGISTER_URL));
                startActivity(intent);
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
        HttpUtils.get(LoginConstants.LOGIN_URL, new StringCallback() {
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

        HttpUtils.post(LoginConstants.LOGIN_URL, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showProgress(false);
            }

            @Override
            public void onResponse(String response, int id) {
                showProgress(false);
                onLoginFinish();
            }
        });
    }

    private void onLoginFinish() {
        HttpUtils.get(LoginConstants.HOME_URL + "/setting", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                AccountInfo accountInfo = AccountParser.parseResponse(response);
                String username = accountInfo.getUserName();

                if (!TextUtils.isEmpty(username)) {
                    AccountManager.setActiveAccountInfo(getActivity(), accountInfo);
                    sendLoginBroadcast();
                    Intent data = new Intent();
                    data.putExtra(LoginConstants.KEY_EXTRA_LOGIN_ACCOUNT, accountInfo);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        });
    }

    private void sendLoginBroadcast() {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
        Intent intent = new Intent(LoginConstants.ACTION_LOGIN_SUCCESS);
        lbm.sendBroadcast(intent);
    }

    private Map<String, String> getLoginParam(String response, String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        String xsrf = HttpUtils.findXsrf(response);
        if (!TextUtils.isEmpty(xsrf)) {
            params.put("_xsrf", xsrf);
        }

//        Log.d("LoginAccountFragment", "getLoginParam -> email: " + email + " password: " + password
//                + " _xsrf: " + xsrf);

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
//        AnimationUtils.showProgress(mProgressView, mLoginFormView, show);
        if (show) {
            mEmailSignInButton.setVisibility(View.GONE);
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            mEmailSignInButton.setVisibility(View.VISIBLE);
            mProgressView.setVisibility(View.GONE);
        }
    }

}