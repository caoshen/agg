package xyz.dcme.agg.ui.post;

import android.os.AsyncTask;
import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.parser.PostParser;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;

public class PostPresenter implements PostContract.Presenter {
    public static final String FIRST_PAGE = "1";
    private static final String TAG = "PostPresenter";
    private PostContract.View mView;

    public PostPresenter(PostContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        HttpUtils.get(Constants.HOME_PAGE + FIRST_PAGE, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.onError();
            }

            @Override
            public void onResponse(String response, int id) {
                List<Post> posts = PostParser.parseHtml(response);
                mView.onRefresh(posts);
            }
        });
    }

    @Override
    public void loadMore(final int nextPage) {
        new AsyncTask<Void, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(Void... voids) {
                return PostParser.parseUrl(Constants.HOME_PAGE + nextPage);
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
