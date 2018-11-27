package cn.okclouder.ovc.ui.reply;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import cn.okclouder.ovc.parser.PostParser;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;
import cn.okclouder.library.util.LogUtils;

public class ReplyPresenter implements ReplyContract.Presenter {

    private static final String LOG_TAG = "ReplyPresenter";
    private ReplyContract.View mView;

    public ReplyPresenter(ReplyContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void start(String name) {
        mView.showIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.REPLY_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(ReplyParser.parseResponse(response));
            }
        });
    }

    @Override
    public void refresh(String name) {
        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.REPLY_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showRefresh(ReplyParser.parseResponse(response));
            }
        });
    }

    @Override
    public void load(String name, final int page) {
        String url = Constants.USER_PROFILE_URL + name + Constants.REPLY_URL
                + "?p=" + page;
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                int total = PostParser.parseTotalCount(response);
                LogUtils.d(LOG_TAG, "load -> total page: " + total + " current page: " + page);
                if (page > total) {
                    return;
                }
                mView.showLoad(ReplyParser.parseResponse(response));
            }
        });
    }
}
