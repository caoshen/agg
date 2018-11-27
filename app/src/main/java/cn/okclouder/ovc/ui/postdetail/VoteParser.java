package cn.okclouder.ovc.ui.postdetail;

import android.util.Log;

import com.alibaba.fastjson.JSON;

public class VoteParser {
    private static final String TAG = "VoteParser";

    public static VoteResp parseResponse(String response) {
        Log.d(TAG, "parseResponse -> response:" + response);
        return JSON.parseObject(response, VoteResp.class);
    }
}
