package xyz.dcme.agg.ui.topic;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import xyz.dcme.agg.util.Constants;

public class TopicPresenter implements TopicContract.Presenter {

    private TopicContract.View mView;

    public TopicPresenter(TopicContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String name) {
        mView.setLoadingIndicator(true);
        new AsyncTask<String, Void, List<Topic>>() {
            @Override
            protected List<Topic> doInBackground(String... names) {
                if (TextUtils.isEmpty(names[0])) {
                    return null;
                }
                return TopicParser.parseList(Constants.USER_PROFILE_URL + names[0]);
            }

            @Override
            protected void onPostExecute(List<Topic> topics) {
                super.onPostExecute(topics);
                if (topics != null && !topics.isEmpty()) {
                    mView.showTopics(topics);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute(name);
    }
}
