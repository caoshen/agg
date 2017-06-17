package xyz.dcme.agg.ui.personal.detail;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import xyz.dcme.agg.util.Constants;

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

    }

    @Override
    public void load(String name) {
        mView.setLoadingIndicator(true);
        new AsyncTask<String, Void, List<Detail>>() {
            @Override
            protected List<Detail> doInBackground(String... names) {
                if (TextUtils.isEmpty(names[0])) {
                    return null;
                }
                return PersonalInfoDetailParser.parseList(Constants.USER_PROFILE_URL + names[0]);
            }

            @Override
            protected void onPostExecute(List<Detail> details) {
                super.onPostExecute(details);
                if (details != null && !details.isEmpty()) {
                    mView.showDetails(details);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute(name);
    }
}
