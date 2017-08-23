package xyz.dcme.agg.ui.notify;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class MessagePresenter implements MessageContract.Presenter {
    private static final String LOG_TAG = "MessagePresenter";
    private final MessageContract.View mView;

    public MessagePresenter(MessageContract.View v) {
        mView = v;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh() {
        mView.showIndicator(true);
        HttpUtils.get(Constants.NOTIFICATION_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(MessageParser.parseResponse(response));
            }
        });
    }

    @Override
    public void load(final int page) {
        String url = Constants.NOTIFICATION_URL + "?p=" + page;
        mView.showIndicator(true);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                int total = MessageParser.parseTotalCount(response);
                LogUtils.d(LOG_TAG, "load -> total page: " + total + " current page: " + page);
                if (page > total) {
                    return;
                }
                mView.showLoad(MessageParser.parseResponse(response));
            }
        });
    }
}
