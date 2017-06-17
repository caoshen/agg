package xyz.dcme.agg.ui.reply;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;

public class ReplyPresenter implements ReplyContract.Presenter {

    private ReplyContract.View mView;

    public ReplyPresenter(ReplyContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }

        mView.setLoadingIndicator(true);

        HttpUtils.get(Constants.USER_PROFILE_URL + name, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onResponse(String response, int id) {
                mView.setLoadingIndicator(false);
                List<Reply> replies = ReplyParser.parseResponse(response);
                mView.showReplies(replies);
            }
        });
    }
}
