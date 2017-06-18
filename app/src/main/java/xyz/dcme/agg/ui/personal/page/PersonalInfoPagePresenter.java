package xyz.dcme.agg.ui.personal.page;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;

public class PersonalInfoPagePresenter implements PersonalInfoPageContract.Presenter {

    private static final String LOG_TAG = "PersonalInfoPagePresenter";
    private final PersonalInfoPageContract.View mView;

    public PersonalInfoPagePresenter(PersonalInfoPageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String name) {
        mView.setLoadingIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setLoadingIndicator(false);
                mView.show(PersonalInfoPageParser.parseResponse(response));
            }
        });
    }
}
