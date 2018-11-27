package cn.okclouder.ovc.frag.user;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HttpUtils;
import cn.okclouder.library.util.LogUtils;

public class UserHomePresenter implements UserHomeContract.Presenter {
    private static final String TAG = UserHomeContract.class.getSimpleName();
    private final UserHomeContract.View mView;

    public UserHomePresenter(UserHomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void query(String name) {
        if (TextUtils.isEmpty(name)) {
            LogUtils.e(TAG, "query -> user name is null");
            return;
        }
        HttpUtils.get(Constants.USER_PROFILE_URL + name, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                UserDetailInfo info = UserDetailParser.parse(response);
                mView.showImage(info.imageUrl);
                mView.showDetails(info.details);
                mView.showCount(info.count);
            }
        });
    }

    @Override
    public void start() {

    }
}
