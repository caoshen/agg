package xyz.dcme.agg.ui.post;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.parser.PostParser;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class PostPresenter implements PostContract.Presenter {
    private static final String FIRST_PAGE = "1";
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
                List<Post> posts = PostParser.parseResponse(response);
                mView.onRefresh(posts);
            }
        });
    }

    @Override
    public void loadMore(final int nextPage) {
        HttpUtils.get(Constants.HOME_PAGE + nextPage, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, e.toString());
                mView.onError();
            }

            @Override
            public void onResponse(String response, int id) {
                List<Post> posts = PostParser.parseResponse(response);
                mView.onLoadMore(posts);
            }
        });
    }
}
