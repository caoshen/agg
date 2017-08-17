package xyz.dcme.agg.ui.news;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;

import okhttp3.Call;
import xyz.dcme.agg.parser.PostParser;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;

public class NewsPresenter implements NewsContract.Presenter {

    private static final int FIRST_PAGE = 1;
    private final NewsContract.View mView;

    public NewsPresenter(NewsContract.View v) {
        mView = v;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh(String tab) {
        String url = getTabUrl(tab, FIRST_PAGE);
        mView.showIndicator(true);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
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
    public void load(String tab, int page) {
        String url = getTabUrl(tab, page);
        mView.showIndicator(true);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                mView.showLoad(PostParser.parseResponse(response));
            }
        });
    }

    private String getTabUrl(String tab, int page) {
        String url = Constants.PREFIX_HOME;
        if (Arrays.asList(Constants.TABS).contains(tab)) {
            url = Constants.PREFIX_TAB + tab + "&p=";
        }
        url += page;
        return url;
    }
}
