package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import xyz.dcme.agg.ui.publish.helper.CommentHelper;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;
import xyz.dcme.agg.util.LoginUtils;

public class PostDetailPresenter implements PostDetailContract.Presenter {
    public static final String LOG_TAG = "PostDetailPresenter";
    private static final String TAG = LogUtils.makeLogTag("PostDetailPresenter");

    private final PostDetailContract.View mView;
    private final CommentHelper mCommentHelper;
    private Context mContext;

    public PostDetailPresenter(PostDetailContract.View view) {
        mView = view;
        mContext = mView.getViewContext();
        view.setPresenter(this);
        mCommentHelper = new CommentHelper();
    }

    @Override
    public void start(String url) {
        mView.showIndicator(true);

        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                if (LoginUtils.needLogin(response)) {
                    mView.startLogin();
                } else {
                    mView.showRefresh(PostDetailParser.parseResponse(response));
                }
                mView.showIndicator(false);
            }
        });
    }

    @Override
    public void load(String url, final int page) {
        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }
        String realUrl = url;
        if (url.contains("#")) {
            realUrl = url.split("#")[0];
        }

        final String finalUrl = realUrl;
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                int total = PostDetailParser.parseTotalCount(response);
                int nextPage = total - (page - 1);

                if (nextPage <= 0) {
                    LogUtils.d(LOG_TAG, "load -> total page: " + total + " next page: " + nextPage);
                    return;
                }
                String nextUrl = finalUrl +  "?p=" + nextPage;
                LogUtils.d(LOG_TAG, "load -> nextUrl: " + nextUrl);
                load(nextUrl);
            }
        });
    }

    @Override
    public void load(String url) {
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.onLoadMore(PostDetailParser.parseComments(response));
            }
        });
    }

    @Override
    public void refresh(String url) {
        mView.showRefreshingIndicator(true);

        if (!url.startsWith(Constants.HOME_URL)) {
            url = Constants.HOME_URL + url;
        }

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showRefreshingIndicator(false);
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showRefreshingIndicator(false);
                mView.showRefresh(PostDetailParser.parseResponse(response));
            }
        });
    }

    @Override
    public void sendComment(final String comment, final String url) {
        mView.showCommentIndicator(true);

        if (mContext == null) {
            LogUtils.e(TAG, "sendComment -> context is null");
            mView.showCommentIndicator(false);
            return;
        }
        if (TextUtils.isEmpty(url)) {
            LogUtils.e(LOG_TAG, "sendComment -> url is null");
            return;
        }

        if (!AccountUtils.hasActiveAccount(mContext)) {
            mView.startLogin();
            return;
        }

        mCommentHelper.sendComment(comment, url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.sendCommentFailed();
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setCommentSuccess();
            }
        });
    }

    @Override
    public void addFavourite(String url) {
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                AddFavouriteResp resp = AddFavouriteParser.parseResponse(response);
                Log.d(TAG, "like -> favourite resp:" + resp.toString());
                mView.showFavouriteAddTips(resp.getTips());
            }
        });
    }

    @Override
    public void like(String url) {
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                VoteResp resp = VoteParser.parseResponse(response);
                Log.d(TAG, "like -> vote resp:" + resp.toString());
                mView.showPostLike(resp.getTips());
            }
        });
    }

    @Override
    public void start() {

    }
}
