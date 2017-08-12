package xyz.dcme.agg.ui.topic;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.parser.PostParser;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class TopicPresenter implements TopicContract.Presenter {

    private static final String LOG_TAG = "TopicPresenter";
    private TopicContract.View mView;

    public TopicPresenter(TopicContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void start(String name) {
        mView.showIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.TOPIC_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(PostParser.parseResponse(response));
            }
        });
    }

    @Override
    public void refresh(String name) {
        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.TOPIC_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showRefresh(PostParser.parseResponse(response));
            }
        });
    }

    @Override
    public void load(String name, final int page) {
        String url = Constants.USER_PROFILE_URL + name + Constants.TOPIC_URL
                + "?p=" + page;
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                int total = PostParser.parseTotalCount(response);
                if (page > total) {
                    LogUtils.d(LOG_TAG, "load -> total page: " + total + " current page: " + page);
                    return;
                }
                mView.showLoad(PostParser.parseResponse(response));
            }
        });
    }
}
