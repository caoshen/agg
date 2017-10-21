package xyz.dcme.agg.ui.postdetail;

import android.text.TextUtils;

import java.util.HashMap;

import xyz.dcme.agg.util.Constants;

/**
 * Add favourite response
 */
public class AddFavouriteResp {
    public static final HashMap<String, String> ERROR_MAP = new HashMap<>();
    public static final int ADD_SUCCESS = 1;

    static {
        ERROR_MAP.put("topic_not_exist", "主题不存在");
        ERROR_MAP.put("can_not_favorite_your_topic", "不能收藏自己的主题");
        ERROR_MAP.put("already_favorited", "之前已经收藏过了");
        ERROR_MAP.put("user_not_login", "请先登录再进行收藏");
        ERROR_MAP.put("not_been_favorited", "之前还没有收藏过");
    }

    private String mMessage;
    // 0: already favourited, 1: favourite success
    private int mSuccess;

    public AddFavouriteResp() {

    }

    public AddFavouriteResp(String message, int success) {
        mMessage = message;
        mSuccess = success;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getSuccess() {
        return mSuccess;
    }

    public void setSuccess(int success) {
        mSuccess = success;
    }

    public String getTips() {
        if (TextUtils.isEmpty(mMessage)) {
            return Constants.EMPTY_STR;
        } else if (addFavSuccess()) {
            return "收藏成功";
        }
        return ERROR_MAP.get(mMessage);
    }

    private boolean addFavSuccess() {
        return mSuccess == ADD_SUCCESS;
    }

    @Override
    public String toString() {
        return "favourite resp, message:" + mMessage + ", success:" + mSuccess;
    }
}
