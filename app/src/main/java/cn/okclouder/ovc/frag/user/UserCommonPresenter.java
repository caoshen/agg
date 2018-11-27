package cn.okclouder.ovc.frag.user;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import cn.okclouder.ovc.parser.PostParser;
import cn.okclouder.ovc.ui.favorite.FavoriteParser;
import cn.okclouder.ovc.ui.reply.ReplyParser;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;

public class UserCommonPresenter implements UserCommonContract.Presenter {
    private final UserCommonRecyclerFragment.USER_ACTION mAction;
    private UserCommonContract.View mView;

    public UserCommonPresenter(UserCommonContract.View view, UserCommonRecyclerFragment.USER_ACTION action) {
        mView = view;
        mAction = action;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void start(String name) {
        mView.showIndicator(true);

        String url = getActionUrl(name);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                if (mAction == UserCommonRecyclerFragment.USER_ACTION.TOPIC) {
                    mView.showRefresh(PostParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.REPLY) {
                    mView.showRefreshedReply(ReplyParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.FAVOURITE) {
                    mView.showRefresh(FavoriteParser.parseResponse(response));
                }
            }
        });
    }

    private String getActionUrl(String name) {
        switch (mAction) {
            case TOPIC: {
                return Constants.USER_PROFILE_URL + name + Constants.TOPIC_URL;
            }
            case REPLY: {
                return Constants.USER_PROFILE_URL + name + Constants.REPLY_URL;
            }
            case FAVOURITE: {
                return Constants.USER_PROFILE_URL + name + Constants.FAVORITES;
            }
            default:
                return Constants.USER_PROFILE_URL + name + Constants.TOPIC_URL;
        }
    }

    private String getActionUrl(String name, int page) {
        return getActionUrl(name) + "?p=" + page;
    }

    @Override
    public void refresh(String name) {
        String url = getActionUrl(name);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                if (mAction == UserCommonRecyclerFragment.USER_ACTION.TOPIC) {
                    mView.showRefresh(PostParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.REPLY) {
                    mView.showRefreshedReply(ReplyParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.FAVOURITE) {
                    mView.showRefresh(FavoriteParser.parseResponse(response));
                }
            }
        });
    }

    @Override
    public void load(String name, final int page) {
        String url = getActionUrl(name, page);

        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.showIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.showIndicator(false);
                if (mAction == UserCommonRecyclerFragment.USER_ACTION.TOPIC) {
                    int total = PostParser.parseTotalCount(response);
                    if (page > total) {
                        return;
                    }
                    mView.showLoad(PostParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.REPLY) {
                    int total = PostParser.parseTotalCount(response);
                    if (page > total) {
                        return;
                    }
                    mView.showLoadedReply(ReplyParser.parseResponse(response));
                } else if (mAction == UserCommonRecyclerFragment.USER_ACTION.FAVOURITE) {
                    int total = PostParser.parseTotalCount(response);
                    if (page > total) {
                        return;
                    }
                    mView.showLoad(FavoriteParser.parseResponse(response));
                }
            }
        });
    }
}
