package cn.okclouder.ovc.ui.personal.detail;

import com.zhy.http.okhttp.callback.StringCallback;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;
import okhttp3.Call;

import static cn.okclouder.library.util.LogUtils.makeLogTag;

public class PersonalInfoDetailPresenter implements PersonalInfoDetailContract.Presenter {
    private static final String TAG = makeLogTag("PersonalInfoDetailPresenter");
    private static final String LOG_TAG = "PersonalInfoDetailPresenter";

    private final PersonalInfoDetailContract.View mView;

    PersonalInfoDetailPresenter(PersonalInfoDetailContract.View v) {
        mView = v;
        v.setPresenter(this);
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
                mView.showDetails(PersonalInfoDetailParser.parseResponse(response));
            }
        });
    }
}
