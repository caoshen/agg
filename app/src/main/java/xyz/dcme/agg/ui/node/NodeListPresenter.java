package xyz.dcme.agg.ui.node;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.parser.PostParser;

public class NodeListPresenter implements NodeListContract.Presenter {
    public static final int FIRST_PAGE = 1;
    public static final String HOME = "home";
    public static final String PREFIX_HOME = "http://www.guanggoo.com/?p=";
    public static final String TAB_LATEST = "latest";
    public static final String TAB_ELITE = "elite";
    public static final String PREFIX_TAB = "http://www.guanggoo.com/?tab=";
    public static final String PREFIX_NODE = "http://www.guanggoo.com/node/";
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
                String prefix = getUrlPrefix(nodeName);
                return PostParser.parseUrl(prefix + FIRST_PAGE);
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                mView.onRefresh(data);
            }
        }.execute();
    }

    private String getUrlPrefix(String nodeName) {
        if (HOME.equals(nodeName)) {
            return PREFIX_HOME;
        } else if (TAB_LATEST.equals(nodeName) || TAB_ELITE.equals(nodeName)) {
            return PREFIX_TAB + nodeName + "&p=";
        } else {
            return PREFIX_NODE + nodeName + "?p=";
        }
    }

    @Override
    public void load(final String nodeName, final int page) {
        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                String prefix = getUrlPrefix(nodeName);
                Log.d(LOG_TAG, prefix + page);
                return PostParser.parseUrl(prefix + page);
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
