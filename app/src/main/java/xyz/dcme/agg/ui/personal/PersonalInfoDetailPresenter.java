package xyz.dcme.agg.ui.personal;

import android.os.AsyncTask;

import java.util.List;

import xyz.dcme.agg.util.Constants;

import static xyz.dcme.agg.util.LogUtils.LOGD;
import static xyz.dcme.agg.util.LogUtils.makeLogTag;

public class PersonalInfoDetailPresenter implements PersonalInfoDetailContract.Presenter {
    private static final String TAG = makeLogTag("PersonalInfoDetailPresenter");

    private final PersonalInfoDetailContract.View mView;

    PersonalInfoDetailPresenter(PersonalInfoDetailContract.View v) {
        mView = v;
        v.setPresenter(this);
    }

    @Override
    public void start() {
        LOGD(TAG, "start");
        mView.setLoadingIndicator(true);
        new AsyncTask<String, Void, List<Detail>>() {
            @Override
            protected List<Detail> doInBackground(String... userId) {
                return PersonalInfoDetailParser.parseList(Constants.WEBSITE_USER_PROFILE_URL + "/" + userId[0]);
            }

            @Override
            protected void onPostExecute(List<Detail> details) {
                super.onPostExecute(details);
                if (details != null && !details.isEmpty()) {
                    mView.showDetails(details);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute("justyouknowwhy");
    }
}
