package cn.okclouder.ovc.ui.favorite;

import com.zhy.http.okhttp.callback.StringCallback;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.parser.PostParser;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;
import okhttp3.Call;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private static final String LOG_TAG = "FavoritePresenter";
    private FavoriteContract.View mView;

    public FavoritePresenter(FavoriteContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void start(String name) {
        mView.showIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.FAVORITES, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(FavoriteParser.parseResponse(response));
            }
        });
    }

    @Override
    public void refresh(String name) {
        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.FAVORITES, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(FavoriteParser.parseResponse(response));
            }
        });
    }

    @Override
    public void load(String name, final int page) {
        String url = Constants.USER_PROFILE_URL + name + Constants.FAVORITES
                + "?p=" + page;
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                int total = PostParser.parseTotalCount(response);
                LogUtils.d(LOG_TAG, "load -> total page: " + total + " current page: " + page);
                if (page > total) {
                    return;
                }
                mView.showLoad(FavoriteParser.parseResponse(response));
            }
        });
    }
}
