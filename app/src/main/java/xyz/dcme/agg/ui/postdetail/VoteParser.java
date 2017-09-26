package xyz.dcme.agg.ui.postdetail;

import com.alibaba.fastjson.JSON;

public class VoteParser {
    public static VoteResp parseResponse(String response) {
        return JSON.parseObject(response, VoteResp.class);
    }
}
