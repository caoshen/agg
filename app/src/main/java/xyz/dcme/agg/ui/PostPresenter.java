package xyz.dcme.agg.ui;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.parser.PostParser;

public class PostPresenter implements PostContract.Presenter {
    private static final String TAG = "PostPresenter";
    private PostContract.View mView;

    public PostPresenter(PostContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
//        new AsyncTask<Void, Void, List<String>>() {
//
//            @Override
//            protected List<String> doInBackground(Void... voids) {
//                return PostParser.parse("http://www.guanggoo.com/?p=1");
//            }
//
//            @Override
//            protected void onPostExecute(List<String> data) {
//                super.onPostExecute(data);
//                mView.onRefresh(data);
//                Log.d(TAG, "" + data.size());
//            }
//        }.execute();

        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                return PostParser.parseUrl("http://www.guanggoo.com/?p=1");
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void loadMore(final int nextPage) {
//        new AsyncTask<Void, Void, List<String>>() {
//
//            @Override
//            protected List<String> doInBackground(Void... voids) {
//                return PostParser.parse("http://www.guanggoo.com/?p=" + nextPage);
//            }
//
//            @Override
//            protected void onPostExecute(List<String> data) {
//                super.onPostExecute(data);
//                mView.onLoadMore(data);
//                Log.d(TAG, "" + data.size());
//            }
//        }.execute();

        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                return PostParser.parseUrl("http://www.guanggoo.com/?p=" + nextPage);
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                mView.onLoadMore(data);
                Log.d(TAG, "" + data.size());
            }
        }.execute();
    }
}
