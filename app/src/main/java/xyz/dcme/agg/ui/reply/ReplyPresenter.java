package xyz.dcme.agg.ui.reply;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import xyz.dcme.agg.util.Constants;

public class ReplyPresenter implements ReplyContract.Presenter {

    private ReplyContract.View mView;

    public ReplyPresenter(ReplyContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String name) {
        mView.setLoadingIndicator(true);
        new AsyncTask<String, Void, List<Reply>>() {
            @Override
            protected List<Reply> doInBackground(String... names) {
                if (TextUtils.isEmpty(names[0])) {
                    return null;
                }
                return ReplyParser.parseList(Constants.WEBSITE_USER_PROFILE_URL + "/" + names[0]);
            }

            @Override
            protected void onPostExecute(List<Reply> replies) {
                super.onPostExecute(replies);
                if (replies != null && !replies.isEmpty()) {
                    mView.showReplies(replies);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute(name);
    }
}
