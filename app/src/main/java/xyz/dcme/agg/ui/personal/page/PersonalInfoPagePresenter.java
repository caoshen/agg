package xyz.dcme.agg.ui.personal.page;

import android.os.AsyncTask;
import android.text.TextUtils;

import xyz.dcme.agg.util.Constants;

public class PersonalInfoPagePresenter implements PersonalInfoPageContract.Presenter {

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
        new AsyncTask<String, Void, Count>() {
            @Override
            protected Count doInBackground(String... names) {
                if (TextUtils.isEmpty(names[0])) {
                    return null;
                }
                return PersonalInfoPageParser.parse(Constants.USER_PROFILE_URL + names[0]);
            }

            @Override
            protected void onPostExecute(Count count) {
                super.onPostExecute(count);
                if (count != null) {
                    mView.show(count);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute(name);
    }
}
