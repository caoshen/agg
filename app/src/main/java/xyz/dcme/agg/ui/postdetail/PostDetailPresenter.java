package xyz.dcme.agg.ui.postdetail;

import android.os.AsyncTask;

import java.util.List;

import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private final PostDetailContract.View mView;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        final String reqUrl = url;
        new AsyncTask<Void, Void, List<PostDetailItem>>() {

            @Override
            protected List<PostDetailItem> doInBackground(Void... voids) {
                return PostDetailParser.parseDetail(reqUrl);
            }

            @Override
            protected void onPostExecute(List<PostDetailItem> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void sendReply(String comment) {
        mView.addComment(comment);
    }

    @Override
    public void start() {

    }
}
