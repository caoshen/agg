package xyz.dcme.agg.ui.postdetail;

import android.os.AsyncTask;

import java.util.List;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private final PostDetailContract.View mView;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDetail(String url) {
        final String reqUrl = url;
        new AsyncTask<Void, Void, List<PostDetailType>>() {

            @Override
            protected List<PostDetailType> doInBackground(Void... voids) {
                return PostDetailParser.parseUrl(reqUrl);
            }

            @Override
            protected void onPostExecute(List<PostDetailType> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void start() {

    }
}
