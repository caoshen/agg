package xyz.dcme.agg.ui.personal;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.ui.personal.page.PersonalInfoContract;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class PersonalInfoPresenter implements PersonalInfoContract.Presenter {
    private static final String LOG_TAG = "PersonalInfoPresenter";
    private PersonalInfoContract.View mView;

    public PersonalInfoPresenter(PersonalInfoContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadDetail(String userName) {
        if (TextUtils.isEmpty(userName)) {
            LogUtils.e(LOG_TAG, "loadDetail -> user name is null");
            return;
        }
        HttpUtils.get(Constants.USER_PROFILE_URL + userName, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showImage(PersonalInfoParser.parseResponse(response));
            }
        });
    }
}
