package cn.okclouder.ovc.ui.postdetail;

import android.text.TextUtils;

import java.util.HashMap;

import cn.okclouder.ovc.util.Constants;

public class VoteResp {
    private static final HashMap<String, String> ERROR_MAP = new HashMap<>();
    private static final int VOTE_SUCCESS = 1;

    static {
        ERROR_MAP.put("topic_not_exist", "主题不存在");
        ERROR_MAP.put("can_not_vote_your_topic", "不能喜欢自己的主题");
        ERROR_MAP.put("already_voted", "感谢已经表示过");
        ERROR_MAP.put("user_not_login", "请先登录再进行评价");
        ERROR_MAP.put("can_not_vote_your_reply", "不能喜欢自己的赞");
    }

    public String message;
    // 0: already voted, 1: vote success
    public int success;

    public String getTips() {
        if (TextUtils.isEmpty(message)) {
            return Constants.EMPTY_STR;
        } else if (voteSuccess()) {
            return "已赞";
        }
        return ERROR_MAP.get(message);
    }

    private boolean voteSuccess() {
        return success == VOTE_SUCCESS;
    }

    @Override
    public String toString() {
        return "vote resp, message:" + message + ", success:" + success;
    }
}
