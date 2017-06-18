package xyz.dcme.agg.ui.favorite;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;

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
    public void load(String name) {
        mView.setLoadingIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name + Constants.FAVORITES, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setLoadingIndicator(false);
                mView.showFav(FavoriteParser.parseResponse(response));
            }
        });
    }
}
