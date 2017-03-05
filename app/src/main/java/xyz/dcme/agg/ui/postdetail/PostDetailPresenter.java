package xyz.dcme.agg.ui.postdetail;

import android.os.AsyncTask;

import java.util.List;

import xyz.dcme.agg.model.PostComment;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private final PostDetailContract.View mView;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        final String reqUrl = url;
        new AsyncTask<Void, Void, List<PostComment>>() {

            @Override
            protected List<PostComment> doInBackground(Void... voids) {
                return PostDetailParser.parseComment(reqUrl);
            }

            @Override
            protected void onPostExecute(List<PostComment> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void start() {

    }
}
