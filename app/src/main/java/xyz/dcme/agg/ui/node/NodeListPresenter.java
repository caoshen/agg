package xyz.dcme.agg.ui.node;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.parser.PostParser;

public class NodeListPresenter implements NodeListContract.Presenter {
    private static final String LOG_TAG = "NodeListPresenter";
    private final NodeListContract.View mView;

    public NodeListPresenter(NodeListContract.View v) {
        mView = v;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void start(final String nodeName) {
        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                return PostParser.parseUrl("http://www.guanggoo.com/node/" + nodeName + "?p=1");
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    @Override
    public void load(final String nodeName, final int page) {
        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                String url = "http://www.guanggoo.com/node/" + nodeName + "?p=" + page;
                Log.d(LOG_TAG, url);
                return PostParser.parseUrl(url);
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                mView.onLoad(data);
                Log.d(LOG_TAG, "" + data.size());
            }
        }.execute();
    }
}
