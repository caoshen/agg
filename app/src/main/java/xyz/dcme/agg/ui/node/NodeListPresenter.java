package xyz.dcme.agg.ui.node;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;

import okhttp3.Call;
import xyz.dcme.agg.parser.PostParser;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.LogUtils;
import xyz.dcme.agg.util.LoginUtils;

public class NodeListPresenter implements NodeListContract.Presenter {
    public static final int FIRST_PAGE = 1;
    public static final String HOME = "home";
    public static final String PREFIX_HOME = "http://www.guanggoo.com/?p=";
    public static final String PREFIX_TAB = "http://www.guanggoo.com/?tab=";
    public static final String PREFIX_NODE = "http://www.guanggoo.com/node/";
    private static final String LOG_TAG = "NodeListPresenter";

    public static final String TAB_LATEST = "latest";
    public static final String TAB_ELITE = "elite";
    public static final String TAB_INTEREST = "interest";
    public static final String TAB_FOLLOWS = "follows";
    public static final String[] TABS = {TAB_LATEST, TAB_ELITE, TAB_INTEREST, TAB_FOLLOWS};

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
        String prefix = getUrlPrefix(nodeName);
        String url = prefix + FIRST_PAGE;

        mView.showIndicator(true);
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                if (LoginUtils.needLogin(response)) {
                    mView.showLoginTips();
                } else {
                    mView.showIndicator(false);
                    mView.showRefresh(PostParser.parseResponse(response));
                }
            }
        });
    }

    @Override
    public void refresh(String nodeName) {
        String prefix = getUrlPrefix(nodeName);
        String url = prefix + FIRST_PAGE;

        HttpUtils.get(url, new StringCallback() {
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

    private String getUrlPrefix(String nodeName) {
        if (HOME.equals(nodeName)) {
            return PREFIX_HOME;
        } else if (Arrays.asList(TABS).contains(nodeName)) {
            return PREFIX_TAB + nodeName + "&p=";
        } else {
            return PREFIX_NODE + nodeName + "?p=";
        }
    }

    @Override
    public void load(final String nodeName, final int page) {
        String prefix = getUrlPrefix(nodeName);
        String url = prefix + page;

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showLoad(PostParser.parseResponse(response));
            }
        });
    }
}
