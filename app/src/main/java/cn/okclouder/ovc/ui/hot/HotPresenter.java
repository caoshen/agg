package cn.okclouder.ovc.ui.hot;

import com.zhy.http.okhttp.callback.StringCallback;

import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;
import okhttp3.Call;

public class
HotPresenter implements HotContract.Presenter {

    private final HotContract.View mView;

    public HotPresenter(HotContract.View v) {
        mView = v;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh() {
        String url = Constants.HOME_URL;
        mView.showIndicator(true);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showError();
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showRefresh(HotParser.parseResponse(response));
            }
        });
    }
}
